package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Emprestimo;

import org.springframework.stereotype.Component;

@Component
public class BuscarEmprestimoComponent extends BaseComponent {

    // --- BUSCA EMPRÉSTIMO POR UID OU LANÇA EXCEÇÃO ---
    public Emprestimo buscarPorUid(Long uid) {
        return findByUid(Emprestimo.class, uid)
                .orElseThrow(() -> new BusinessException("Empréstimo não encontrado."));
    }
}
