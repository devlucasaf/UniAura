package erp.uniaura.modules.tcc.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.modules.tcc.dto.AnalisarEntregaTccRequestDTO;
import erp.uniaura.modules.tcc.dto.EntregaTccResponseDTO;
import erp.uniaura.modules.tcc.model.EntregaTcc;
import erp.uniaura.modules.tcc.model.StatusEntregaTcc;
import erp.uniaura.modules.tcc.model.StatusTcc;
import erp.uniaura.modules.tcc.model.Tcc;
import erp.uniaura.modules.tcc.model.TipoEntregaTcc;
import erp.uniaura.modules.tcc.repository.EntregaTccRepository;
import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EntregaTccService {

    private static final String SUBDIR_ENTREGAS_TCC = "entregas-tcc";

    private final EntregaTccRepository entregaTccRepository;
    private final TccService tccService;
    private final StorageService storageService;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    // --- ENVIA UMA ENTREGA (PROJETO, PARCIAL OU VERSÃO FINAL) DO TCC ---
    @Transactional
    public EntregaTccResponseDTO enviar(Long tccId, TipoEntregaTcc tipo, MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new BusinessException("É obrigatório enviar um arquivo.");
        }

        Tcc tcc = tccService.buscarEntidade(tccId);
        Usuario autenticado = usuarioAutenticadoOuFalha();
        validarDonoOuEquipe(tcc, autenticado);

        if (tcc.getStatus() != StatusTcc.EM_ANDAMENTO) {
            throw new BusinessException("Só é possível enviar entregas enquanto o TCC está em andamento.");
        }

        EntregaTcc entrega = EntregaTcc.builder()
                .tcc(tcc)
                .tipo(tipo)
                .arquivoUrl(storageService.store(arquivo, SUBDIR_ENTREGAS_TCC))
                .status(StatusEntregaTcc.PENDENTE)
                .build();

        return toResponse(entregaTccRepository.save(entrega));
    }

    // --- LISTA AS ENTREGAS DE UM TCC ---
    @Transactional(readOnly = true)
    public Page<EntregaTccResponseDTO> listarPorTcc(Long tccId, Pageable pageable) {
        return entregaTccRepository.findByTccId(tccId, pageable).map(this::toResponse);
    }

    // --- APROVA OU REJEITA UMA ENTREGA DO TCC ---
    @Transactional
    public EntregaTccResponseDTO analisar(Long id, AnalisarEntregaTccRequestDTO dto) {
        EntregaTcc entrega = buscarEntidade(id);

        if (entrega.getStatus() != StatusEntregaTcc.PENDENTE) {
            throw new BusinessException("Esta entrega já foi analisada.");
        }

        entrega.setStatus(dto.getStatus());
        entrega.setObservacoesOrientador(dto.getObservacoesOrientador());
        entrega.setAnalisadoEm(LocalDateTime.now());

        return toResponse(entregaTccRepository.save(entrega));
    }

    private EntregaTcc buscarEntidade(Long id) {
        return entregaTccRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega de TCC", id));
    }

    // --- SÓ O PRÓPRIO ALUNO DO TCC OU A EQUIPE ACADÊMICA PODEM ENVIAR ENTREGAS ---
    private void validarDonoOuEquipe(Tcc tcc, Usuario autenticado) {
        boolean equipe = autenticado.getRole() == TipoUsuario.ADMIN
                || autenticado.getRole() == TipoUsuario.COORDENADOR
                || autenticado.getRole() == TipoUsuario.PROFESSOR;

        if (equipe) {
            return;
        }

        boolean dono = tcc.getAluno().getUsuario() != null
                && tcc.getAluno().getUsuario().getId().equals(autenticado.getId());
        if (!dono) {
            throw new BusinessException("Você não pode enviar entregas para o TCC de outro aluno.");
        }
    }

    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }

    private EntregaTccResponseDTO toResponse(EntregaTcc e) {
        return EntregaTccResponseDTO.builder()
                .id(e.getId())
                .tccId(e.getTcc().getId())
                .tipo(e.getTipo())
                .arquivoUrl(e.getArquivoUrl())
                .status(e.getStatus())
                .observacoesOrientador(e.getObservacoesOrientador())
                .analisadoEm(e.getAnalisadoEm())
                .enviadoEm(e.getEnviadoEm())
                .build();
    }
}
