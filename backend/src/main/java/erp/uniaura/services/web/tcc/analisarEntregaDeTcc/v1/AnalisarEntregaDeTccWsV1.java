package erp.uniaura.services.web.tcc.analisarEntregaDeTcc.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class AnalisarEntregaDeTccWsV1 extends BaseWs {

    private final AnalisarEntregaDeTccServiceV1 analisarEntregaDeTccServiceV1;

    @PostMapping
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Aprova ou rejeita uma entrega do TCC",
            description = "Grava o resultado e as observações do orientador. O professor só analisa entregas de TCC em que é orientador ou coorientador. "
                    + "Erro 422 se a entrega não existir, se o professor não for orientador do TCC, se a entrega já foi analisada ou se o resultado informado for PENDENTE.")
    public void analisar(@LoggedInUserId String usuarioId, @Valid @RequestBody AnalisarEntregaDeTccParamsV1 params) {
        analisarEntregaDeTccServiceV1.analisar(Long.valueOf(usuarioId), params);
    }
}
