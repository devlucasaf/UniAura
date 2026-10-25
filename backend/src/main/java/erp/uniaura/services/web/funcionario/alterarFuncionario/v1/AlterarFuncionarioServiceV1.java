package erp.uniaura.services.web.funcionario.alterarFuncionario.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Funcionario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.funcionario.BuscarFuncionarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarFuncionarioServiceV1 extends BaseService {

    private final BuscarFuncionarioComponent buscarFuncionarioComponent;

    // --- ALTERA OS DADOS DO FUNCIONÁRIO E DO USUÁRIO ---
    @Transactional
    public void alterar(AlterarFuncionarioParamsV1 params) {
        Funcionario funcionario = buscarFuncionarioComponent.buscarPorUid(params.getUid());
        Usuario usuario = funcionario.getUsuario();

        usuario.setNome(params.getNome());
        usuario.setEmail(params.getEmail());
        usuario.setCpf(params.getCpf());
        usuario.setTelefone(params.getTelefone());
        usuario.setDataNascimento(params.getDataNascimento());

        if (!funcionario.getCargo().equals(params.getCargo())) {
            usuario.setRole(params.getCargo().toRoleUsuario());
            funcionario.setCargo(params.getCargo());
        }

        usuario.merge();

        funcionario.setDataAdmissao(params.getDataAdmissao());
        funcionario.setDepartamento(params.getDepartamento());
        funcionario.merge();
    }
}
