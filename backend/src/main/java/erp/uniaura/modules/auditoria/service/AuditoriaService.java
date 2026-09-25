package erp.uniaura.modules.auditoria.service;

import erp.uniaura.modules.auditoria.dto.LogAuditoriaResponseDTO;
import erp.uniaura.modules.auditoria.model.LogAuditoria;
import erp.uniaura.modules.auditoria.repository.LogAuditoriaRepository;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final LogAuditoriaRepository logAuditoriaRepository;

    // --- GRAVA UM EVENTO DE AUDITORIA ---
    @Transactional
    public void registrar(Usuario usuario, String acao, String entidade, Object entidadeId, String detalhes) {
        try {
            LogAuditoria log = LogAuditoria.builder()
                    .usuario(usuario)
                    .acao(acao)
                    .entidade(entidade)
                    .entidadeId(entidadeId == null ? null : entidadeId.toString())
                    .detalhes(detalhes)
                    .build();
            logAuditoriaRepository.save(log);
        } catch (RuntimeException ignorada) {
            // --- AUDITORIA É "BEST EFFORT" ---
        }
    }

    // --- LISTA OS LOGS ---
    @Transactional(readOnly = true)
    public Page<LogAuditoriaResponseDTO> listar(String entidade, UUID usuarioId, Pageable pageable) {
        Page<LogAuditoria> page;
        if (entidade != null && !entidade.isBlank()) {
            page = logAuditoriaRepository.findByEntidadeIgnoreCase(entidade, pageable);
        } else if (usuarioId != null) {
            page = logAuditoriaRepository.findByUsuarioId(usuarioId, pageable);
        } else {
            page = logAuditoriaRepository.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    private LogAuditoriaResponseDTO toResponse(LogAuditoria log) {
        return LogAuditoriaResponseDTO.builder()
                .id(log.getId())
                .usuarioId(log.getUsuario() == null ? null : log.getUsuario().getId())
                .usuarioNome(log.getUsuario() == null ? null : log.getUsuario().getNome())
                .acao(log.getAcao())
                .entidade(log.getEntidade())
                .entidadeId(log.getEntidadeId())
                .detalhes(log.getDetalhes())
                .criadoEm(log.getCriadoEm())
                .build();
    }
}
