package erp.uniaura.services.web.comunicado.removerComunicado.v1;

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
public class RemoverComunicadoWsV1 extends BaseWs {

    private final RemoverComunicadoServiceV1 removerComunicadoServiceV1;

    // --- ENDPOINT QUE REMOVE UM COMUNICADO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Remove um comunicado",
            description = "Remove o comunicado identificado pelo uid. Só o autor, a coordenação ou o admin podem remover. "
                    + "Erro 422 se o comunicado não existir ou se o usuário não puder removê-lo.")
    public void remover(@LoggedInUserId String usuarioId, @Valid @RequestBody RemoverComunicadoParamsV1 params) {
        removerComunicadoServiceV1.remover(Long.valueOf(usuarioId), params);
    }
}
