package erp.uniaura.services.web.biblioteca.pesquisarExemplaresDoLivro.v1;

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
public class PesquisarExemplaresDoLivroWsV1 extends BaseWs {

    private final PesquisarExemplaresDoLivroServiceV1 pesquisarExemplaresDoLivroServiceV1;

    // --- ENDPOINT QUE LISTA OS EXEMPLARES DE UM LIVRO DE FORMA PAGINADA ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Pesquisa os exemplares de um livro de forma paginada",
            description = "Lista os exemplares físicos do livro com código de barras, localização e status. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o livro não existir.")
    public PesquisarExemplaresDoLivroRetornoV1 pesquisar(@Valid @ParameterObject PesquisarExemplaresDoLivroParamsV1 params) {
        return pesquisarExemplaresDoLivroServiceV1.pesquisar(params);
    }
}
