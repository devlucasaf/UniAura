package erp.uniaura.services.web.nota.alterarNota.v1;

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
public class AlterarNotaWsV1 extends BaseWs {

    private final AlterarNotaServiceV1 alterarNotaServiceV1;

    // --- ENDPOINT QUE ALTERA UMA NOTA LANÇADA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Altera uma nota lançada",
            description = "Atualiza a nota e registra quem alterou. Só o professor responsável pela disciplina, a coordenação ou o admin podem alterar. "
                    + "Erro 422 se a nota não existir ou se o usuário não for o professor responsável.")
    public void alterar(@LoggedInUserId String usuarioId, @Valid @RequestBody AlterarNotaParamsV1 params) {
        alterarNotaServiceV1.alterar(Long.valueOf(usuarioId), params);
    }
}
