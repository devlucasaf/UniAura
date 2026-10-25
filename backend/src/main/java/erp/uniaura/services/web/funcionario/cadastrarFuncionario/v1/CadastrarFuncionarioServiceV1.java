package erp.uniaura.services.web.funcionario.cadastrarFuncionario.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.Uid;

import erp.uniaura.services.common.usuario.CriarUsuarioComponent;
import erp.uniaura.persistence.entities.Funcionario;
import erp.uniaura.persistence.entities.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarFuncionarioServiceV1 extends BaseService {
    private final CriarUsuarioComponent criarUsuarioComponent;

    // --- CRIA O USUÁRIO E O FUNCIONÁRIO VINCULADO ---
    @Transactional
    public Uid cadastrar(CadastrarFuncionarioParamsV1 params) {
        Usuario usuario = criarUsuarioComponent.criar(params.getNome(), params.getEmail(), params.getSenha(), params.getCpf(), params.getTelefone(), params.getDataNascimento(), true, params.getCargo().toRoleUsuario());

        Funcionario funcionario = new Funcionario();

        funcionario.setUsuario(usuario);
        funcionario.setCargo(params.getCargo());
        funcionario.setDataAdmissao(params.getDataAdmissao());
        funcionario.setDepartamento(params.getDepartamento());
        funcionario.persist();

        return new Uid(funcionario.getUid());
    }
}
