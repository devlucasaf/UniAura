package erp.uniaura.services.web.funcionario.cadastrarFuncionario.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.Uid;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class CadastrarFuncionarioWsV1 extends BaseWs {

    private final CadastrarFuncionarioServiceV1 cadastrarFuncionarioServiceV1;

    // --- ENDPOINT QUE CADASTRA UM FUNCIONÁRIO E SEU USUÁRIO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Cadastra um funcionário",
            description = "Cria o funcionário e o usuário vinculado (com o perfil do cargo) e devolve o uid do funcionário. "
                    + "Erro 422 se já existir usuário com o mesmo e-mail.")
    public Uid cadastrar(@Valid @RequestBody CadastrarFuncionarioParamsV1 params) {
        return cadastrarFuncionarioServiceV1.cadastrar(params);
    }
}
