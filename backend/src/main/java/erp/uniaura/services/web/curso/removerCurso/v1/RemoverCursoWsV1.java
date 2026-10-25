package erp.uniaura.services.web.curso.removerCurso.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class RemoverCursoWsV1 extends BaseWs {

    private final RemoverCursoServiceV1 removerCursoServiceV1;

    // --- ENDPOINT QUE REMOVE UM CURSO ---
    @PostMapping
    @HasAuthority("ROLE_ADMIN")
    @Operation(summary = "Remove um curso",
            description = "Remove o curso identificado pelo uid. Erro 422 se o curso não existir.")
    public void remover(@Valid @RequestBody RemoverCursoParamsV1 params) {
        removerCursoServiceV1.remover(params);
    }
}
