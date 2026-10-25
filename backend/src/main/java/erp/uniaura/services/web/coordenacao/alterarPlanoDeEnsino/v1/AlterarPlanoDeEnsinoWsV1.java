package erp.uniaura.services.web.coordenacao.alterarPlanoDeEnsino.v1;

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
public class AlterarPlanoDeEnsinoWsV1 extends BaseWs {

    private final AlterarPlanoDeEnsinoServiceV1 alterarPlanoDeEnsinoServiceV1;

    // --- O PROFESSOR RESPONSÁVEL, A COORDENAÇÃO E O ADMIN EDITAM O CONTEÚDO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Altera o conteúdo de um plano de ensino",
            description = "Só é possível enquanto o plano está em RASCUNHO ou DEVOLVIDO. "
                    + "Erro 422 se o plano não existir, se o usuário não for o professor responsável ou se o plano estiver em outro status.")
    public void alterar(@LoggedInUserId String usuarioId, @Valid @RequestBody AlterarPlanoDeEnsinoParamsV1 params) {
        alterarPlanoDeEnsinoServiceV1.alterar(Long.valueOf(usuarioId), params);
    }
}
