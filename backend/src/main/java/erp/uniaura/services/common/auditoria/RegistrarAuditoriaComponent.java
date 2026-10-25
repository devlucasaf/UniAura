package erp.uniaura.services.common.auditoria;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.LogAuditoria;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RegistrarAuditoriaComponent extends BaseComponent {

    // --- GRAVA UM EVENTO DE AUDITORIA; A AUDITORIA É BEST EFFORT E NUNCA DERRUBA A OPERAÇÃO ---
    @Transactional
    public void registrar(Usuario usuario, String acao, String entidade, Object entidadeId, String detalhes) {
        try {
            LogAuditoria log = new LogAuditoria();

            log.setUsuario(usuario);
            log.setAcao(acao);
            log.setEntidade(entidade);
            log.setDetalhes(detalhes);

            if (entidadeId != null) {
                log.setEntidadeId(entidadeId.toString());
            }

            log.persist();
        } catch (RuntimeException ignorada) {
            // --- FALHA AO AUDITAR NÃO IMPEDE A OPERAÇÃO PRINCIPAL ---
        }
    }
}
