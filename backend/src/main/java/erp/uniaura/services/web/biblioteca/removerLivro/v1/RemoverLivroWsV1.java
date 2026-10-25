package erp.uniaura.services.web.biblioteca.removerLivro.v1;

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
public class RemoverLivroWsV1 extends BaseWs {

    private final RemoverLivroServiceV1 removerLivroServiceV1;

    // --- ENDPOINT QUE REMOVE UM LIVRO DO ACERVO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Remove um livro do acervo",
            description = "Apaga o livro e a capa. Erro 422 se o livro não existir ou se ainda tiver exemplares cadastrados.")
    public void remover(@Valid @RequestBody RemoverLivroParamsV1 params) {
        removerLivroServiceV1.remover(params);
    }
}
