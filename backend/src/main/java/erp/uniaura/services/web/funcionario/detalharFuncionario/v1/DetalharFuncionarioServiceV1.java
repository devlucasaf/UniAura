package erp.uniaura.services.web.funcionario.detalharFuncionario.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Funcionario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.funcionario.BuscarFuncionarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharFuncionarioServiceV1 extends BaseService {

    private final BuscarFuncionarioComponent buscarFuncionarioComponent;

    // --- DETALHA O FUNCIONÁRIO E SEUS DADOS DE USUÁRIO ---
    @Transactional(readOnly = true)
    public DetalharFuncionarioRetornoV1 detalhar(DetalharFuncionarioParamsV1 params) {
        Funcionario funcionario = buscarFuncionarioComponent.buscarPorUid(params.getUid());
        Usuario usuario = funcionario.getUsuario();

        DetalharFuncionarioRetornoV1 retorno = new DetalharFuncionarioRetornoV1();

        retorno.setUid(funcionario.getUid());
        retorno.setNome(usuario.getNome());
        retorno.setEmail(usuario.getEmail());
        retorno.setCpf(usuario.getCpf());
        retorno.setTelefone(usuario.getTelefone());
        retorno.setDataNascimento(usuario.getDataNascimento());
        retorno.setCargo(funcionario.getCargo());
        retorno.setDataAdmissao(funcionario.getDataAdmissao());
        retorno.setDepartamento(funcionario.getDepartamento());
        retorno.setCriadoEm(funcionario.getCriadoEm());
        retorno.setAtualizadoEm(funcionario.getAtualizadoEm());

        return retorno;
    }
}
