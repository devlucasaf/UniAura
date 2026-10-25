package erp.uniaura.services.web.usuario.detalharUsuario.v1;

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
public class DetalharUsuarioWsV1 extends BaseWs {

    private final DetalharUsuarioServiceV1 detalharUsuarioServiceV1;

    // --- SÓ O ADMIN, A COORDENAÇÃO E A SECRETARIA CONSULTAM UM USUÁRIO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Detalha um usuário",
            description = "Devolve os dados do usuário, sem a senha. Erro 422 se o usuário não existir.")
    public DetalharUsuarioRetornoV1 detalhar(@Valid @ParameterObject DetalharUsuarioParamsV1 params) {
        return detalharUsuarioServiceV1.detalhar(params);
    }
}
