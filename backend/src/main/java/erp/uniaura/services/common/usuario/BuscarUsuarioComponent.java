package erp.uniaura.services.common.usuario;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class BuscarUsuarioComponent extends BaseComponent {

    // --- PELO id SEQUENCIAL, QUE É O QUE O TOKEN TRAZ NA CLAIM user_id ---
    public Usuario buscarPorId(Long id) {
        return findById(Usuario.class, id)
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }

    // --- PELO uid, QUE É O IDENTIFICADOR PÚBLICO INFORMADO PELA EQUIPE NAS FUNCIONALIDADES ---
    public Usuario buscarPorUid(Long uid) {
        return findByUid(Usuario.class, uid)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
    }
}
