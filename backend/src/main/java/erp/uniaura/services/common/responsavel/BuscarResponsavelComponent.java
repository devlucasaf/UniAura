package erp.uniaura.services.common.responsavel;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Responsavel;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarResponsavelComponent extends BaseComponent {

    // --- BUSCA RESPONSÁVEL POR UID OU LANÇA EXCEÇÃO ---
    public Responsavel buscarPorUid(Long uid) {
        return findByUid(Responsavel.class, uid)
                .orElseThrow(() -> new BusinessException("Responsável não encontrado."));
    }

    // --- PELO USUÁRIO LOGADO, QUANDO O PRÓPRIO RESPONSÁVEL FAZ A OPERAÇÃO ---
    public Responsavel buscarPorUsuarioId(Long usuarioId) {
        List<Responsavel> encontrados = em()
                .createQuery("select r from Responsavel r where r.usuario.id = :usuarioId", Responsavel.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();

        if (encontrados.isEmpty()) {
            throw new BusinessException("O usuário autenticado não possui cadastro de responsável.");
        }

        return encontrados.get(0);
    }
}
