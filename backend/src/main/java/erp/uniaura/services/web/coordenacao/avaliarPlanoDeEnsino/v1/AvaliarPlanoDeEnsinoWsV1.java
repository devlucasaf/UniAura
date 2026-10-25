package erp.uniaura.services.web.coordenacao.avaliarPlanoDeEnsino.v1;

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
public class AvaliarPlanoDeEnsinoWsV1 extends BaseWs {

    private final AvaliarPlanoDeEnsinoServiceV1 avaliarPlanoDeEnsinoServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN AVALIAM OS PLANOS ---
    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Avalia um plano de ensino submetido",
            description = "Aprova, devolve para ajuste ou reprova o plano e registra o passo no histórico. "
                    + "Erro 422 se o plano não existir, se não estiver SUBMETIDO, se o desfecho for inválido ou se faltar o parecer ao devolver ou reprovar.")
    public void avaliar(@LoggedInUserId String usuarioId, @Valid @RequestBody AvaliarPlanoDeEnsinoParamsV1 params) {
        avaliarPlanoDeEnsinoServiceV1.avaliar(Long.valueOf(usuarioId), params);
    }
}
