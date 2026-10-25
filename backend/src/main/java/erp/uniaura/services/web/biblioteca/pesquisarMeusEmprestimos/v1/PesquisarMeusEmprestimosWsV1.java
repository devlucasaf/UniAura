package erp.uniaura.services.web.biblioteca.pesquisarMeusEmprestimos.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarMeusEmprestimosWsV1 extends BaseWs {

    private final PesquisarMeusEmprestimosServiceV1 pesquisarMeusEmprestimosServiceV1;

    // --- ENDPOINT QUE LISTA OS EMPRÉSTIMOS DO USUÁRIO LOGADO ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Pesquisa os empréstimos do usuário autenticado de forma paginada",
            description = "Lista só os empréstimos do usuário logado. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo).")
    public PesquisarMeusEmprestimosRetornoV1 pesquisar(@LoggedInUserId String usuarioId, @Valid @ParameterObject PesquisarMeusEmprestimosParamsV1 params) {
        return pesquisarMeusEmprestimosServiceV1.pesquisar(Long.valueOf(usuarioId), params);
    }
}
