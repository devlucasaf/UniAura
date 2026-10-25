package erp.uniaura.services.web.responsavel.pesquisarAlunosDoResponsavel.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarAlunosDoResponsavelWsV1 extends BaseWs {

    private final PesquisarAlunosDoResponsavelServiceV1 pesquisarAlunosDoResponsavelServiceV1;

    // --- ENDPOINT QUE LISTA OS ALUNOS VINCULADOS AO RESPONSÁVEL ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Lista os alunos vinculados a um responsável",
            description = "Devolve os alunos vinculados ao responsável identificado pelo uid. Erro 422 se o responsável não existir.")
    public PesquisarAlunosDoResponsavelRetornoV1 pesquisar(@Valid @ParameterObject PesquisarAlunosDoResponsavelParamsV1 params) {
        return pesquisarAlunosDoResponsavelServiceV1.pesquisar(params);
    }
}
