package erp.uniaura.services.web.atividade.avaliarEntrega.v1;

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
public class AvaliarEntregaWsV1 extends BaseWs {

    private final AvaliarEntregaServiceV1 avaliarEntregaServiceV1;

    // --- O PROFESSOR, A COORDENAÇÃO E O ADMIN AVALIAM UMA ENTREGA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Avalia a entrega de um aluno",
            description = "Grava a nota e o feedback e muda o status da entrega para AVALIADA. Só o professor dono da atividade, a coordenação ou o admin podem avaliar. "
                    + "Erro 422 se a entrega não existir, se o usuário não for o professor responsável ou se a nota ultrapassar o valor máximo da atividade.")
    public void avaliar(@LoggedInUserId String usuarioId, @Valid @RequestBody AvaliarEntregaParamsV1 params) {
        avaliarEntregaServiceV1.avaliar(Long.valueOf(usuarioId), params);
    }
}
