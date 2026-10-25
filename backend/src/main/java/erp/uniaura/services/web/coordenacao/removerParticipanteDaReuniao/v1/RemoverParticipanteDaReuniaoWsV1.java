package erp.uniaura.services.web.coordenacao.removerParticipanteDaReuniao.v1;

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
public class RemoverParticipanteDaReuniaoWsV1 extends BaseWs {

    private final RemoverParticipanteDaReuniaoServiceV1 removerParticipanteDaReuniaoServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN REMOVEM CONVOCADOS ---
    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Remove um convocado da reunião do colegiado",
            description = "Retira o usuário da lista de convocados de uma reunião ainda AGENDADA. "
                    + "Erro 422 se a reunião ou o usuário não existirem, se a reunião já foi realizada ou cancelada ou se o usuário não foi convocado.")
    public void remover(@Valid @RequestBody RemoverParticipanteDaReuniaoParamsV1 params) {
        removerParticipanteDaReuniaoServiceV1.remover(params);
    }
}
