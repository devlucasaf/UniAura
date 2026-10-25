package erp.uniaura.services.web.curso.detalharCurso.v1;

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
public class DetalharCursoWsV1 extends BaseWs {

    private final DetalharCursoServiceV1 detalharCursoServiceV1;

    // --- ENDPOINT QUE DETALHA UM CURSO PELO UID ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Detalha um curso",
            description = "Devolve os dados completos do curso identificado pelo uid. Erro 422 se o curso não existir.")
    public DetalharCursoRetornoV1 detalhar(@Valid @ParameterObject DetalharCursoParamsV1 params) {
        return detalharCursoServiceV1.detalhar(params);
    }
}
