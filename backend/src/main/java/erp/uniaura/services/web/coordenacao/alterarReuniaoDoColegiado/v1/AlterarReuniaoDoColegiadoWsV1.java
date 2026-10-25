package erp.uniaura.services.web.coordenacao.alterarReuniaoDoColegiado.v1;

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
public class AlterarReuniaoDoColegiadoWsV1 extends BaseWs {

    private final AlterarReuniaoDoColegiadoServiceV1 alterarReuniaoDoColegiadoServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN ALTERAM REUNIÕES ---
    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Altera uma reunião do colegiado",
            description = "Atualiza curso, título, data, local e pauta de uma reunião ainda AGENDADA. "
                    + "Erro 422 se a reunião ou o curso não existirem ou se a reunião já foi realizada ou cancelada.")
    public void alterar(@Valid @RequestBody AlterarReuniaoDoColegiadoParamsV1 params) {
        alterarReuniaoDoColegiadoServiceV1.alterar(params);
    }
}
