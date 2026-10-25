package erp.uniaura.services.web.disciplina.detalharDisciplina.v1;

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
public class DetalharDisciplinaWsV1 extends BaseWs {

    private final DetalharDisciplinaServiceV1 detalharDisciplinaServiceV1;

    // --- ENDPOINT QUE DETALHA UMA DISCIPLINA PELO UID ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Detalha uma disciplina",
            description = "Devolve os dados completos da disciplina identificada pelo uid. Erro 422 se a disciplina não existir.")
    public DetalharDisciplinaRetornoV1 detalhar(@Valid @ParameterObject DetalharDisciplinaParamsV1 params) {
        return detalharDisciplinaServiceV1.detalhar(params);
    }
}
