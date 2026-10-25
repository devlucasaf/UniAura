package erp.uniaura.services.web.biblioteca.pesquisarEmprestimosDoUsuario.v1;

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
public class PesquisarEmprestimosDoUsuarioWsV1 extends BaseWs {

    private final PesquisarEmprestimosDoUsuarioServiceV1 pesquisarEmprestimosDoUsuarioServiceV1;

    // --- ENDPOINT QUE LISTA OS EMPRÉSTIMOS DE UM USUÁRIO DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Pesquisa os empréstimos de um usuário de forma paginada",
            description = "Para a equipe da biblioteca consultar o histórico de qualquer usuário. O próprio usuário usa 'pesquisarMeusEmprestimos'. "
                    + "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o usuário não existir.")
    public PesquisarEmprestimosDoUsuarioRetornoV1 pesquisar(@Valid @ParameterObject PesquisarEmprestimosDoUsuarioParamsV1 params) {
        return pesquisarEmprestimosDoUsuarioServiceV1.pesquisar(params);
    }
}
