package erp.uniaura.services.web.nota.removerNota.v1;

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
public class RemoverNotaWsV1 extends BaseWs {

    private final RemoverNotaServiceV1 removerNotaServiceV1;

    // --- ENDPOINT QUE REMOVE UMA NOTA LANÇADA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Remove uma nota lançada",
            description = "Só o professor responsável pela disciplina, a coordenação ou o admin podem remover. Erro 422 se a nota não existir ou se o usuário não for o professor responsável.")
    public void remover(@LoggedInUserId String usuarioId, @Valid @RequestBody RemoverNotaParamsV1 params) {
        removerNotaServiceV1.remover(Long.valueOf(usuarioId), params);
    }
}
