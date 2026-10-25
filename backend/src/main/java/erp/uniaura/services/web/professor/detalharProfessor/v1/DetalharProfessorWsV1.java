package erp.uniaura.services.web.professor.detalharProfessor.v1;

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
public class DetalharProfessorWsV1 extends BaseWs {

    private final DetalharProfessorServiceV1 detalharProfessorServiceV1;

    // --- ENDPOINT QUE DETALHA UM PROFESSOR PELO UID ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Detalha um professor",
            description = "Devolve os dados completos do professor (incluindo os do usuário vinculado) identificado pelo uid. Erro 422 se o professor não existir.")
    public DetalharProfessorRetornoV1 detalhar(@Valid @ParameterObject DetalharProfessorParamsV1 params) {
        return detalharProfessorServiceV1.detalhar(params);
    }
}
