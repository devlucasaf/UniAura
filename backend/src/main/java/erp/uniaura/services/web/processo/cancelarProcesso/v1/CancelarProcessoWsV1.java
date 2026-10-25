package erp.uniaura.services.web.processo.cancelarProcesso.v1;

import cloudsupport.security.HasAuthority;
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
public class CancelarProcessoWsV1 extends BaseWs {

    private final CancelarProcessoServiceV1 cancelarProcessoServiceV1;

    // --- ENDPOINT QUE CANCELA UM PROCESSO PELO ALUNO AUTOR ---
    @PostMapping
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Cancela um processo ainda não concluído",
            description = "Só o aluno autor do processo pode cancelá-lo. "
                    + "Erro 422 se o processo não existir, se o usuário não for o autor ou se o processo já estiver DEFERIDO, INDEFERIDO ou CANCELADO.")
    public void cancelar(@LoggedInUserId String usuarioId, @Valid @RequestBody CancelarProcessoParamsV1 params) {
        cancelarProcessoServiceV1.cancelar(Long.valueOf(usuarioId), params);
    }
}
