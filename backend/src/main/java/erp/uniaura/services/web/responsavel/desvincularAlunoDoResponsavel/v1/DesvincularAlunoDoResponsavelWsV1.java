package erp.uniaura.services.web.responsavel.desvincularAlunoDoResponsavel.v1;

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
public class DesvincularAlunoDoResponsavelWsV1 extends BaseWs {

    private final DesvincularAlunoDoResponsavelServiceV1 desvincularAlunoDoResponsavelServiceV1;

    // --- ENDPOINT QUE DESVINCULA O ALUNO DO RESPONSÁVEL ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_SECRETARIA"})
    @Operation(summary = "Desvincula um aluno de um responsável",
            description = "Remove o vínculo entre o responsável e o aluno. Erro 422 se o responsável ou o aluno não existirem, "
                    + "ou se não houver vínculo entre eles.")
    public void desvincular(@Valid @RequestBody DesvincularAlunoDoResponsavelParamsV1 params) {
        desvincularAlunoDoResponsavelServiceV1.desvincular(params);
    }
}
