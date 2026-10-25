package erp.uniaura.services.web.matricula.detalharMatricula.v1;

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
public class DetalharMatriculaWsV1 extends BaseWs {

    private final DetalharMatriculaServiceV1 detalharMatriculaServiceV1;

    // --- ENDPOINT QUE DETALHA UMA MATRÍCULA PELO UID ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Detalha uma matrícula",
            description = "Devolve os dados da matrícula identificada pelo uid. Erro 422 se a matrícula não existir.")
    public DetalharMatriculaRetornoV1 detalhar(@Valid @ParameterObject DetalharMatriculaParamsV1 params) {
        return detalharMatriculaServiceV1.detalhar(params);
    }
}
