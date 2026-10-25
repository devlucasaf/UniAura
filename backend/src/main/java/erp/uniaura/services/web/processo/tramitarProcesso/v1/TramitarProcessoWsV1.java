package erp.uniaura.services.web.processo.tramitarProcesso.v1;

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
public class TramitarProcessoWsV1 extends BaseWs {

    private final TramitarProcessoServiceV1 tramitarProcessoServiceV1;

    // --- ENDPOINT QUE TRAMITA O PROCESSO PARA UM NOVO STATUS ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Movimenta o processo para um novo status, registrando o despacho",
            description = "Muda o status, registra o passo na linha do tempo e torna quem tramita o responsável pelo processo. "
                    + "Erro 422 se o processo não existir, se já estiver DEFERIDO, INDEFERIDO ou CANCELADO, se o status novo for igual ao atual, "
                    + "se a transição não for permitida ou se faltar o parecer ao deferir ou indeferir.")
    public void tramitar(@LoggedInUserId String usuarioId, @Valid @RequestBody TramitarProcessoParamsV1 params) {
        tramitarProcessoServiceV1.tramitar(Long.valueOf(usuarioId), params);
    }
}
