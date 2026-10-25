package erp.uniaura.services.web.comunicado.alterarComunicado.v1;

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
public class AlterarComunicadoWsV1 extends BaseWs {

    private final AlterarComunicadoServiceV1 alterarComunicadoServiceV1;

    // --- ENDPOINT QUE ALTERA UM COMUNICADO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Altera um comunicado",
            description = "Atualiza o comunicado identificado pelo uid. Só o autor, a coordenação ou o admin podem alterar. "
                    + "Erro 422 se o comunicado ou a turma não existirem, ou se o usuário não puder alterar. Sem 'importante', o comunicado deixa de ser importante.")
    public void alterar(@LoggedInUserId String usuarioId, @Valid @RequestBody AlterarComunicadoParamsV1 params) {
        alterarComunicadoServiceV1.alterar(Long.valueOf(usuarioId), params);
    }
}
