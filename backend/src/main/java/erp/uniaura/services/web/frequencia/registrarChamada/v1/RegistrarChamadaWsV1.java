package erp.uniaura.services.web.frequencia.registrarChamada.v1;

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
public class RegistrarChamadaWsV1 extends BaseWs {

    private final RegistrarChamadaServiceV1 registrarChamadaServiceV1;

    // --- ENDPOINT QUE REGISTRA AS PRESENÇAS E FALTAS DA AULA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Registra as presenças e faltas de uma aula em lote",
            description = "Atualiza a presença e a justificativa de cada aluno informado; a justificativa só vale para faltas. "
                    + "Só o professor responsável pela disciplina, a coordenação ou o admin podem registrar. "
                    + "Erro 422 se a aula não existir, se o usuário não for o professor responsável ou se algum aluno não tiver registro nesta aula.")
    public void registrar(@LoggedInUserId String usuarioId, @Valid @RequestBody RegistrarChamadaParamsV1 params) {
        registrarChamadaServiceV1.registrar(Long.valueOf(usuarioId), params);
    }
}
