package erp.uniaura.services.common.funcionario;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Funcionario;

import org.springframework.stereotype.Component;

@Component
public class BuscarFuncionarioComponent extends BaseComponent {

    // --- BUSCA FUNCIONÁRIO POR UID OU LANÇA EXCEÇÃO ---
    public Funcionario buscarPorUid(Long uid) {
        return findByUid(Funcionario.class, uid)
                .orElseThrow(() -> new BusinessException("Funcionário não encontrado."));
    }
}
