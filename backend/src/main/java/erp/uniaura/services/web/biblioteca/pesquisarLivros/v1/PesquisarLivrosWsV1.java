package erp.uniaura.services.web.biblioteca.pesquisarLivros.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarLivrosWsV1 extends BaseWs {

    private final PesquisarLivrosServiceV1 pesquisarLivrosServiceV1;

    // --- ENDPOINT QUE LISTA OS LIVROS DO ACERVO DE FORMA PAGINADA ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Pesquisa livros do acervo de forma paginada",
            description = "Filtra opcionalmente por parte do título, parte do autor, categoria e ISBN, e informa quantos exemplares cada livro tem e quantos estão disponíveis. "
                    + "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo).")
    public PesquisarLivrosRetornoV1 pesquisar(@Valid @ParameterObject PesquisarLivrosParamsV1 params) {
        return pesquisarLivrosServiceV1.pesquisar(params);
    }
}
