package erp.uniaura.services.web.disciplina.pesquisarPrerequisitosDaDisciplina.v1;

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
public class PesquisarPrerequisitosDaDisciplinaWsV1 extends BaseWs {

    private final PesquisarPrerequisitosDaDisciplinaServiceV1 pesquisarPrerequisitosDaDisciplinaServiceV1;

    // --- ENDPOINT QUE LISTA OS PRÉ-REQUISITOS DE UMA DISCIPLINA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa os pré-requisitos de uma disciplina",
            description = "Lista os pré-requisitos da disciplina identificada por 'disciplinaUid'. Erro 422 se a disciplina não existir.")
    public PesquisarPrerequisitosDaDisciplinaRetornoV1 pesquisar(@Valid @ParameterObject PesquisarPrerequisitosDaDisciplinaParamsV1 params) {
        return pesquisarPrerequisitosDaDisciplinaServiceV1.pesquisar(params);
    }
}
