package erp.uniaura.services.web.material.alterarMaterial.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Ws
@RequiredArgsConstructor
public class AlterarMaterialWsV1 extends BaseWs {

    private final AlterarMaterialServiceV1 alterarMaterialServiceV1;

    // --- ENDPOINT QUE ALTERA UM MATERIAL DIDÁTICO ---
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Altera um material didático",
            description = "Atualiza o material identificado pelo uid. Os dados vão na parte 'dados' (JSON) e o arquivo novo, se houver, na parte 'arquivo'; sem arquivo novo, o atual é mantido. "
                    + "Só o professor dono do material, a coordenação ou o admin podem alterar. Erro 422 se o material ou o vínculo não existirem.")
    public void alterar(@LoggedInUserId String usuarioId,
                        @Valid @RequestPart("dados") AlterarMaterialParamsV1 params,
                        @RequestPart(value = "arquivo", required = false) MultipartFile arquivo) {
        alterarMaterialServiceV1.alterar(Long.valueOf(usuarioId), params, arquivo);
    }
}
