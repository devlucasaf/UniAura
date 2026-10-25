package erp.uniaura.services.web.material.removerMaterial.v1;

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
public class RemoverMaterialWsV1 extends BaseWs {

    private final RemoverMaterialServiceV1 removerMaterialServiceV1;

    // --- ENDPOINT QUE REMOVE UM MATERIAL DIDÁTICO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Remove um material didático",
            description = "Remove o material identificado pelo uid e apaga o arquivo guardado, se houver. "
                    + "Só o professor dono do material, a coordenação ou o admin podem remover. Erro 422 se o material não existir.")
    public void remover(@LoggedInUserId String usuarioId, @Valid @RequestBody RemoverMaterialParamsV1 params) {
        removerMaterialServiceV1.remover(Long.valueOf(usuarioId), params);
    }
}
