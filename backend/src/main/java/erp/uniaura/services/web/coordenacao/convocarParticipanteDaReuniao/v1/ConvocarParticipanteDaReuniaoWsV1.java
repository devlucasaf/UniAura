package erp.uniaura.services.web.coordenacao.convocarParticipanteDaReuniao.v1;

import cloudsupport.security.HasAnyAuthority;
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
public class ConvocarParticipanteDaReuniaoWsV1 extends BaseWs {

    private final ConvocarParticipanteDaReuniaoServiceV1 convocarParticipanteDaReuniaoServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN CONVOCAM PARTICIPANTES ---
    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Convoca um participante para a reunião do colegiado",
            description = "Devolve o uid do participante convocado. "
                    + "Erro 422 se a reunião ou o usuário não existirem, se a reunião já foi realizada ou cancelada ou se o usuário já foi convocado.")
    public Uid convocar(@Valid @RequestBody ConvocarParticipanteDaReuniaoParamsV1 params) {
        return convocarParticipanteDaReuniaoServiceV1.convocar(params);
    }
}
