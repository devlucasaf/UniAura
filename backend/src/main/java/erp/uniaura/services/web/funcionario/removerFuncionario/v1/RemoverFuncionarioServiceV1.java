package erp.uniaura.services.web.funcionario.removerFuncionario.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Funcionario;
import erp.uniaura.services.common.funcionario.BuscarFuncionarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverFuncionarioServiceV1 extends BaseService {

    private final BuscarFuncionarioComponent buscarFuncionarioComponent;

    // --- REMOVE O FUNCIONÁRIO PELO UID ---
    @Transactional
    public void remover(RemoverFuncionarioParamsV1 params) {
        Funcionario funcionario = buscarFuncionarioComponent.buscarPorUid(params.getUid());
        funcionario.remove();
    }
}
