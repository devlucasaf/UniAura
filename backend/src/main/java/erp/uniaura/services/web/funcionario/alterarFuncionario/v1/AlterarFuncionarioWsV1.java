package erp.uniaura.services.web.funcionario.alterarFuncionario.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class AlterarFuncionarioWsV1 extends BaseWs {

    private final AlterarFuncionarioServiceV1 alterarFuncionarioServiceV1;

    // --- ENDPOINT QUE ALTERA UM FUNCIONÁRIO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Altera um funcionário",
            description = "Atualiza os dados do funcionário e do usuário vinculado, identificado pelo uid. A senha não é alterada aqui. "
                    + "Trocar o cargo troca também o perfil de acesso. Erro 422 se o funcionário não existir.")
    public void alterar(@Valid @RequestBody AlterarFuncionarioParamsV1 params) {
        alterarFuncionarioServiceV1.alterar(params);
    }
}
