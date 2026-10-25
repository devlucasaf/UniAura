package erp.uniaura.services.web.nota.lancarNota.v1;

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
public class LancarNotaWsV1 extends BaseWs {

    private final LancarNotaServiceV1 lancarNotaServiceV1;

    // --- ENDPOINT QUE LANÇA UMA NOTA PARA UM ALUNO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Lança uma nota para um aluno",
            description = "Grava a nota, registra quem lançou, avisa o aluno por e-mail e devolve o uid gerado. Só o professor responsável pela disciplina, a coordenação ou o admin podem lançar. "
                    + "Erro 422 se o aluno ou o vínculo não existirem ou se o usuário não for o professor responsável.")
    public Uid lancar(@LoggedInUserId String usuarioId, @Valid @RequestBody LancarNotaParamsV1 params) {
        return lancarNotaServiceV1.lancar(Long.valueOf(usuarioId), params);
    }
}
