package erp.uniaura.services.web.professor.removerProfessor.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class RemoverProfessorWsV1 extends BaseWs {

    private final RemoverProfessorServiceV1 removerProfessorServiceV1;

    // --- ENDPOINT QUE REMOVE UM PROFESSOR ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Remove um professor",
            description = "Remove o professor identificado pelo uid. O usuário vinculado não é removido. Erro 422 se o professor não existir.")
    public void remover(@Valid @RequestBody RemoverProfessorParamsV1 params) {
        removerProfessorServiceV1.remover(params);
    }
}
