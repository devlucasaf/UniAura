package erp.uniaura.services.web.biblioteca.detalharLivro.v1;

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
public class DetalharLivroWsV1 extends BaseWs {

    private final DetalharLivroServiceV1 detalharLivroServiceV1;

    // --- ENDPOINT QUE DETALHA UM LIVRO DO ACERVO COM SEUS EXEMPLARES ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Detalha um livro do acervo",
            description = "Devolve os dados completos do livro identificado pelo uid, com a quantidade de exemplares. Erro 422 se o livro não existir.")
    public DetalharLivroRetornoV1 detalhar(@Valid @ParameterObject DetalharLivroParamsV1 params) {
        return detalharLivroServiceV1.detalhar(params);
    }
}
