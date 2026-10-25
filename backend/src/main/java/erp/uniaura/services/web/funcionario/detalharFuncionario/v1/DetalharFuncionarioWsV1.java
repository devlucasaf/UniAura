package erp.uniaura.services.web.funcionario.detalharFuncionario.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class DetalharFuncionarioWsV1 extends BaseWs {

    private final DetalharFuncionarioServiceV1 detalharFuncionarioServiceV1;

    // --- ENDPOINT QUE DETALHA UM FUNCIONÁRIO PELO UID ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Detalha um funcionário",
            description = "Devolve os dados completos do funcionário (incluindo os do usuário vinculado) identificado pelo uid. Erro 422 se o funcionário não existir.")
    public DetalharFuncionarioRetornoV1 detalhar(@Valid @ParameterObject DetalharFuncionarioParamsV1 params) {
        return detalharFuncionarioServiceV1.detalhar(params);
    }
}
