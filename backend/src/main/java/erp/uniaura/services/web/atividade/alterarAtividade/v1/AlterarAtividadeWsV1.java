package erp.uniaura.services.web.atividade.alterarAtividade.v1;

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
public class AlterarAtividadeWsV1 extends BaseWs {

    private final AlterarAtividadeServiceV1 alterarAtividadeServiceV1;

    // --- O PROFESSOR, A COORDENAÇÃO E O ADMIN ALTERAM UMA ATIVIDADE ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Altera uma atividade",
            description = "Atualiza os metadados da atividade. Só o professor dono da atividade, a coordenação ou o admin podem alterar. "
                    + "Erro 422 se a atividade ou o vínculo não existirem, se o usuário não for o professor responsável ou se a data de entrega for anterior à data de postagem.")
    public void alterar(@LoggedInUserId String usuarioId, @Valid @RequestBody AlterarAtividadeParamsV1 params) {
        alterarAtividadeServiceV1.alterar(Long.valueOf(usuarioId), params);
    }
}
