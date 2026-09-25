package erp.uniaura.modules.documento.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioDetails;
import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.documento.dto.AnalisarDocumentoRequestDTO;
import erp.uniaura.modules.documento.dto.DocumentoAlunoResponseDTO;
import erp.uniaura.modules.documento.model.DocumentoAluno;
import erp.uniaura.modules.documento.model.StatusDocumento;
import erp.uniaura.modules.documento.model.TipoDocumento;
import erp.uniaura.modules.documento.repository.DocumentoAlunoRepository;
import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentoAlunoService {

    private static final String SUBDIR_DOCUMENTOS = "documentos-alunos";

    private final DocumentoAlunoRepository documentoAlunoRepository;
    private final AlunoRepository alunoRepository;
    private final StorageService storageService;

    // --- ENVIA UM DOCUMENTO: O PRÓPRIO ALUNO ENVIA O SEU, OU A SECRETARIA/COORDENAÇÃO ENVIA EM NOME DELE ---
    @Transactional
    public DocumentoAlunoResponseDTO enviar(UUID alunoIdInformado, TipoDocumento tipo, MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new BusinessException("É obrigatório enviar um arquivo.");
        }

        Usuario autenticado = usuarioAutenticadoOuFalha();
        Aluno aluno = resolverAluno(alunoIdInformado, autenticado);

        DocumentoAluno documento = DocumentoAluno.builder()
                .aluno(aluno)
                .tipo(tipo)
                .nomeArquivo(arquivo.getOriginalFilename())
                .arquivoUrl(storageService.store(arquivo, SUBDIR_DOCUMENTOS))
                .status(StatusDocumento.PENDENTE)
                .build();

        return toResponse(documentoAlunoRepository.save(documento));
    }

    // --- LISTA OS DOCUMENTOS DE UM ALUNO (VISÃO DA SECRETARIA/COORDENAÇÃO) ---
    @Transactional(readOnly = true)
    public Page<DocumentoAlunoResponseDTO> listarPorAluno(UUID alunoId, Pageable pageable) {
        return documentoAlunoRepository.findByAlunoId(alunoId, pageable).map(this::toResponse);
    }

    // --- LISTA OS DOCUMENTOS DO ALUNO AUTENTICADO ---
    @Transactional(readOnly = true)
    public Page<DocumentoAlunoResponseDTO> meus(Pageable pageable) {
        Aluno aluno = alunoDoAutenticado();
        return documentoAlunoRepository.findByAlunoId(aluno.getId(), pageable).map(this::toResponse);
    }

    // --- APROVA OU REJEITA UM DOCUMENTO ENVIADO ---
    @Transactional
    public DocumentoAlunoResponseDTO analisar(UUID id, AnalisarDocumentoRequestDTO dto) {
        DocumentoAluno documento = buscarEntidade(id);
        Usuario autenticado = usuarioAutenticadoOuFalha();

        documento.setStatus(dto.getStatus());
        documento.setObservacoes(dto.getObservacoes());
        documento.setAnalisadoPor(autenticado);
        documento.setAnalisadoEm(LocalDateTime.now());

        return toResponse(documentoAlunoRepository.save(documento));
    }

    // --- REMOVE UM DOCUMENTO: O DONO SÓ PODE EXCLUIR ENQUANTO ELE AINDA ESTIVER PENDENTE ---
    @Transactional
    public void deletar(UUID id) {
        DocumentoAluno documento = buscarEntidade(id);
        Usuario autenticado = usuarioAutenticadoOuFalha();

        boolean equipe = autenticado.getRole() == TipoUsuario.ADMIN
                || autenticado.getRole() == TipoUsuario.COORDENADOR
                || autenticado.getRole() == TipoUsuario.SECRETARIA;

        if (!equipe) {
            boolean dono = documento.getAluno().getUsuario() != null
                    && documento.getAluno().getUsuario().getId().equals(autenticado.getId());
            if (!dono) {
                throw new BusinessException("Você não pode remover documentos de outro aluno.");
            }
            if (documento.getStatus() != StatusDocumento.PENDENTE) {
                throw new BusinessException("Só é possível remover documentos ainda pendentes de análise.");
            }
        }

        storageService.delete(documento.getArquivoUrl());
        documentoAlunoRepository.delete(documento);
    }

    // --- HELPERS ---

    private DocumentoAluno buscarEntidade(UUID id) {
        return documentoAlunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento", id));
    }

    // --- RESOLVE O ALUNO DONO DO UPLOAD: O PRÓPRIO ALUNO, OU O INFORMADO PELA EQUIPE ADMINISTRATIVA ---
    private Aluno resolverAluno(UUID alunoIdInformado, Usuario autenticado) {
        if (autenticado.getRole() == TipoUsuario.ALUNO) {
            return alunoRepository.findByUsuarioId(autenticado.getId())
                    .orElseThrow(() -> new BusinessException("O usuário autenticado não possui matrícula de aluno."));
        }

        if (alunoIdInformado == null) {
            throw new BusinessException("Informe o aluno para o qual o documento está sendo enviado.");
        }
        return alunoRepository.findById(alunoIdInformado)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", alunoIdInformado));
    }

    private Aluno alunoDoAutenticado() {
        Usuario autenticado = usuarioAutenticadoOuFalha();
        return alunoRepository.findByUsuarioId(autenticado.getId())
                .orElseThrow(() -> new BusinessException("O usuário autenticado não possui matrícula de aluno."));
    }

    private Usuario usuarioAutenticadoOuFalha() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UsuarioDetails ud)) {
            throw new BusinessException("Usuário autenticado não identificado.");
        }
        return ud.getUsuario();
    }

    private DocumentoAlunoResponseDTO toResponse(DocumentoAluno d) {
        return DocumentoAlunoResponseDTO.builder()
                .id(d.getId())
                .alunoId(d.getAluno().getId())
                .alunoNome(d.getAluno().getUsuario() == null ? null : d.getAluno().getUsuario().getNome())
                .tipo(d.getTipo())
                .nomeArquivo(d.getNomeArquivo())
                .arquivoUrl(d.getArquivoUrl())
                .status(d.getStatus())
                .observacoes(d.getObservacoes())
                .analisadoPorNome(d.getAnalisadoPor() == null ? null : d.getAnalisadoPor().getNome())
                .analisadoEm(d.getAnalisadoEm())
                .enviadoEm(d.getEnviadoEm())
                .build();
    }
}
