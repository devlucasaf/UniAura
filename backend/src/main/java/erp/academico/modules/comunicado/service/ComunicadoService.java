package erp.academico.modules.comunicado.service;

import erp.academico.exception.BusinessException;
import erp.academico.exception.ResourceNotFoundException;
import erp.academico.infra.security.UsuarioDetails;
import erp.academico.modules.aluno.model.Aluno;
import erp.academico.modules.aluno.repository.AlunoRepository;
import erp.academico.modules.comunicado.dto.ComunicadoRequestDTO;
import erp.academico.modules.comunicado.dto.ComunicadoResponseDTO;
import erp.academico.modules.comunicado.model.Comunicado;
import erp.academico.modules.comunicado.model.PublicoAlvoComunicado;
import erp.academico.modules.comunicado.repository.ComunicadoRepository;
import erp.academico.modules.usuario.model.TipoUsuario;
import erp.academico.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComunicadoService {

    private final ComunicadoRepository comunicadoRepository;
    private final AlunoRepository alunoRepository;

    // --- LISTA COMUNICADOS (VISÃO ADMINISTRATIVA), COM FILTRO OPCIONAL POR PÚBLICO-ALVO ---
    @Transactional(readOnly = true)
    public Page<ComunicadoResponseDTO> listar(PublicoAlvoComunicado publicoAlvo, Pageable pageable) {
        Page<Comunicado> page = publicoAlvo == null
                ? comunicadoRepository.findAll(pageable)
                : comunicadoRepository.findByPublicoAlvo(publicoAlvo, pageable);
        return page.map(this::toResponse);
    }

    // --- MURAL: OS ÚLTIMOS COMUNICADOS RELEVANTES PARA O USUÁRIO AUTENTICADO ---
    @Transactional(readOnly = true)
    public List<ComunicadoResponseDTO> mural() {
        Usuario autenticado = usuarioAutenticadoOuFalha();

        if (autenticado.getRole() == TipoUsuario.ALUNO) {
            Aluno aluno = alunoRepository.findByUsuarioId(autenticado.getId()).orElse(null);
            List<Comunicado> gerais = comunicadoRepository.findTop30ByPublicoAlvoInOrderByCriadoEmDesc(
                    List.of(PublicoAlvoComunicado.TODOS, PublicoAlvoComunicado.ALUNOS));

            if (aluno == null || aluno.getTurmaAtualId() == null) {
                return gerais.stream().map(this::toResponse).toList();
            }

            List<Comunicado> daTurma = comunicadoRepository.findTop30ByPublicoAlvoAndTurmaIdOrderByCriadoEmDesc(
                    PublicoAlvoComunicado.TURMA, aluno.getTurmaAtualId());

            return java.util.stream.Stream.concat(gerais.stream(), daTurma.stream())
                    .sorted((a, b) -> b.getCriadoEm().compareTo(a.getCriadoEm()))
                    .map(this::toResponse)
                    .toList();
        }

        if (autenticado.getRole() == TipoUsuario.PROFESSOR) {
            return comunicadoRepository.findTop30ByPublicoAlvoInOrderByCriadoEmDesc(
                            List.of(PublicoAlvoComunicado.TODOS, PublicoAlvoComunicado.PROFESSORES))
                    .stream().map(this::toResponse).toList();
        }

        // --- DEMAIS PERFIS (SECRETARIA, COORDENAÇÃO, ADMIN ETC.) VEEM AO MENOS OS COMUNICADOS GERAIS ---
        return comunicadoRepository.findTop30ByPublicoAlvoInOrderByCriadoEmDesc(List.of(PublicoAlvoComunicado.TODOS))
                .stream().map(this::toResponse).toList();
    }

    // --- CRIA UM COMUNICADO EM NOME DO USUÁRIO AUTENTICADO ---
    @Transactional
    public ComunicadoResponseDTO criar(ComunicadoRequestDTO dto) {
        Usuario autenticado = usuarioAutenticadoOuFalha();

        if (dto.getPublicoAlvo() == PublicoAlvoComunicado.TURMA && dto.getTurmaId() == null) {
            throw new BusinessException("Informe a turma quando o público-alvo for TURMA.");
        }

        Comunicado comunicado = Comunicado.builder()
                .titulo(dto.getTitulo())
                .mensagem(dto.getMensagem())
                .publicoAlvo(dto.getPublicoAlvo())
                .turmaId(dto.getPublicoAlvo() == PublicoAlvoComunicado.TURMA ? dto.getTurmaId() : null)
                .importante(Boolean.TRUE.equals(dto.getImportante()))
                .autor(autenticado)
                .build();

        return toResponse(comunicadoRepository.save(comunicado));
    }

    // --- ATUALIZA UM COMUNICADO (SOMENTE O AUTOR OU COORDENAÇÃO/ADMIN) ---
    @Transactional
    public ComunicadoResponseDTO atualizar(UUID id, ComunicadoRequestDTO dto) {
        Comunicado comunicado = buscarEntidade(id);
        validarPodeEditar(comunicado, usuarioAutenticadoOuFalha());

        if (dto.getPublicoAlvo() == PublicoAlvoComunicado.TURMA && dto.getTurmaId() == null) {
            throw new BusinessException("Informe a turma quando o público-alvo for TURMA.");
        }

        comunicado.setTitulo(dto.getTitulo());
        comunicado.setMensagem(dto.getMensagem());
        comunicado.setPublicoAlvo(dto.getPublicoAlvo());
        comunicado.setTurmaId(dto.getPublicoAlvo() == PublicoAlvoComunicado.TURMA ? dto.getTurmaId() : null);
        comunicado.setImportante(Boolean.TRUE.equals(dto.getImportante()));

        return toResponse(comunicadoRepository.save(comunicado));
    }

    // --- REMOVE UM COMUNICADO (SOMENTE O AUTOR OU COORDENAÇÃO/ADMIN) ---
    @Transactional
    public void deletar(UUID id) {
        Comunicado comunicado = buscarEntidade(id);
        validarPodeEditar(comunicado, usuarioAutenticadoOuFalha());
        comunicadoRepository.delete(comunicado);
    }

    // --- HELPERS ---

    private Comunicado buscarEntidade(UUID id) {
        return comunicadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comunicado", id));
    }

    private void validarPodeEditar(Comunicado comunicado, Usuario autenticado) {
        boolean equipeDireção = autenticado.getRole() == TipoUsuario.ADMIN
                || autenticado.getRole() == TipoUsuario.COORDENADOR;

        if (equipeDireção || comunicado.getAutor().getId().equals(autenticado.getId())) {
            return;
        }

        throw new BusinessException("Apenas o autor ou a coordenação podem alterar este comunicado.");
    }

    private Usuario usuarioAutenticadoOuFalha() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UsuarioDetails ud)) {
            throw new BusinessException("Usuário autenticado não identificado.");
        }
        return ud.getUsuario();
    }

    private ComunicadoResponseDTO toResponse(Comunicado c) {
        return ComunicadoResponseDTO.builder()
                .id(c.getId())
                .titulo(c.getTitulo())
                .mensagem(c.getMensagem())
                .publicoAlvo(c.getPublicoAlvo())
                .turmaId(c.getTurmaId())
                .importante(c.getImportante())
                .autorId(c.getAutor().getId())
                .autorNome(c.getAutor().getNome())
                .criadoEm(c.getCriadoEm())
                .atualizadoEm(c.getAtualizadoEm())
                .build();
    }
}
