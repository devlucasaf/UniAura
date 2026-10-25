package erp.uniaura.services.web.atividade.removerAtividade.v1;

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
public class RemoverAtividadeWsV1 extends BaseWs {

    private final RemoverAtividadeServiceV1 removerAtividadeServiceV1;

    // --- ENDPOINT QUE REMOVE UMA ATIVIDADE E SEUS ARQUIVOS ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Remove uma atividade",
            description = "Apaga a atividade, as entregas dos alunos e os arquivos entregues. Só o professor dono da atividade, a coordenação ou o admin podem remover. "
                    + "Erro 422 se a atividade não existir ou se o usuário não for o professor responsável.")
    public void remover(@LoggedInUserId String usuarioId, @Valid @RequestBody RemoverAtividadeParamsV1 params) {
        removerAtividadeServiceV1.remover(Long.valueOf(usuarioId), params);
    }
}
