package erp.uniaura.services.web.coordenacao.agendarReuniaoDoColegiado.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.Uid;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class AgendarReuniaoDoColegiadoWsV1 extends BaseWs {

    private final AgendarReuniaoDoColegiadoServiceV1 agendarReuniaoDoColegiadoServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN AGENDAM REUNIÕES ---
    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Agenda uma reunião do colegiado",
            description = "Cria a reunião com status AGENDADA e devolve o uid gerado. Erro 422 se o curso não existir.")
    public Uid agendar(@LoggedInUserId String usuarioId, @Valid @RequestBody AgendarReuniaoDoColegiadoParamsV1 params) {
        return agendarReuniaoDoColegiadoServiceV1.agendar(Long.valueOf(usuarioId), params);
    }
}
