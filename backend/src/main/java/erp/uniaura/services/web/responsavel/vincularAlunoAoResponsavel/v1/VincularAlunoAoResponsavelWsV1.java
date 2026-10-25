package erp.uniaura.services.web.responsavel.vincularAlunoAoResponsavel.v1;

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
public class VincularAlunoAoResponsavelWsV1 extends BaseWs {

    private final VincularAlunoAoResponsavelServiceV1 vincularAlunoAoResponsavelServiceV1;

    // --- ENDPOINT QUE VINCULA O ALUNO AO RESPONSÁVEL ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_SECRETARIA"})
    @Operation(summary = "Vincula um aluno a um responsável",
            description = "Cria o vínculo entre o responsável e o aluno. Erro 422 se o responsável ou o aluno não existirem, "
                    + "ou se o aluno já estiver vinculado a esse responsável.")
    public void vincular(@Valid @RequestBody VincularAlunoAoResponsavelParamsV1 params) {
        vincularAlunoAoResponsavelServiceV1.vincular(params);
    }
}
