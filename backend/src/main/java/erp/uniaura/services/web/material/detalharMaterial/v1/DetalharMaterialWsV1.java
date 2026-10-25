package erp.uniaura.services.web.material.detalharMaterial.v1;

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
public class DetalharMaterialWsV1 extends BaseWs {

    private final DetalharMaterialServiceV1 detalharMaterialServiceV1;

    // --- ENDPOINT QUE DETALHA UM MATERIAL DIDÁTICO PELO UID ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR", "ROLE_ALUNO", "ROLE_RESPONSAVEL"})
    @Operation(summary = "Detalha um material didático",
            description = "Devolve os dados do material identificado pelo uid. Erro 422 se o material não existir.")
    public DetalharMaterialRetornoV1 detalhar(@Valid @ParameterObject DetalharMaterialParamsV1 params) {
        return detalharMaterialServiceV1.detalhar(params);
    }
}
