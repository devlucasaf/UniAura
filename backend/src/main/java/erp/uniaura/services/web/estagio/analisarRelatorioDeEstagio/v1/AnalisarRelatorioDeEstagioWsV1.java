package erp.uniaura.services.web.estagio.analisarRelatorioDeEstagio.v1;

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
public class AnalisarRelatorioDeEstagioWsV1 extends BaseWs {

    private final AnalisarRelatorioDeEstagioServiceV1 analisarRelatorioDeEstagioServiceV1;

    // --- ENDPOINT QUE ANALISA UM RELATÓRIO DE ESTÁGIO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Aprova ou rejeita um relatório de estágio",
            description = "Grava o resultado, as observações, quem analisou e quando; ao aprovar, soma as horas do relatório à carga horária cumprida do estágio. "
                    + "Erro 422 se o relatório não existir, se já tiver sido analisado ou se o resultado informado for PENDENTE.")
    public void analisar(@LoggedInUserId String usuarioId, @Valid @RequestBody AnalisarRelatorioDeEstagioParamsV1 params) {
        analisarRelatorioDeEstagioServiceV1.analisar(Long.valueOf(usuarioId), params);
    }
}
