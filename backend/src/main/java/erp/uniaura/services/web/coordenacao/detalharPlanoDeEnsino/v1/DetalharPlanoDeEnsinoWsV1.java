package erp.uniaura.services.web.coordenacao.detalharPlanoDeEnsino.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class DetalharPlanoDeEnsinoWsV1 extends BaseWs {

    private final DetalharPlanoDeEnsinoServiceV1 detalharPlanoDeEnsinoServiceV1;

    // --- O PROFESSOR RESPONSÁVEL, A COORDENAÇÃO E O ADMIN CONSULTAM O PLANO COM O HISTÓRICO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Detalha um plano de ensino",
            description = "Devolve o conteúdo e o histórico do plano, achado pelo 'uid' ou pelo 'turmaDisciplinaUid'. "
                    + "Erro 422 se nenhum dos dois for informado, se o plano não existir ou se o professor logado não for o responsável.")
    public DetalharPlanoDeEnsinoRetornoV1 detalhar(@LoggedInUserId String usuarioId, @Valid @ParameterObject DetalharPlanoDeEnsinoParamsV1 params) {
        return detalharPlanoDeEnsinoServiceV1.detalhar(Long.valueOf(usuarioId), params);
    }
}
