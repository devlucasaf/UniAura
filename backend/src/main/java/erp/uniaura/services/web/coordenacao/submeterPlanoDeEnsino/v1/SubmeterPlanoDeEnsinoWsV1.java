package erp.uniaura.services.web.coordenacao.submeterPlanoDeEnsino.v1;

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
public class SubmeterPlanoDeEnsinoWsV1 extends BaseWs {

    private final SubmeterPlanoDeEnsinoServiceV1 submeterPlanoDeEnsinoServiceV1;

    // --- O PROFESSOR RESPONSÁVEL, A COORDENAÇÃO E O ADMIN ENVIAM O PLANO PARA AVALIAÇÃO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Submete um plano de ensino à avaliação da coordenação",
            description = "Passa o plano para SUBMETIDO e registra o passo no histórico. "
                    + "Erro 422 se o plano não existir, se o usuário não for o professor responsável ou se o plano não estiver em RASCUNHO ou DEVOLVIDO.")
    public void submeter(@LoggedInUserId String usuarioId, @Valid @RequestBody SubmeterPlanoDeEnsinoParamsV1 params) {
        submeterPlanoDeEnsinoServiceV1.submeter(Long.valueOf(usuarioId), params);
    }
}
