package erp.uniaura.services.web.biblioteca.cadastrarExemplar.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.Uid;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class CadastrarExemplarWsV1 extends BaseWs {

    private final CadastrarExemplarServiceV1 cadastrarExemplarServiceV1;

    // --- ENDPOINT QUE CADASTRA UM EXEMPLAR FÍSICO DE UM LIVRO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Cadastra um exemplar físico de um livro",
            description = "Cria o exemplar DISPONIVEL e devolve o uid gerado; sem código de barras informado, gera um automaticamente. Erro 422 se o livro não existir ou se o código de barras já estiver em uso.")
    public Uid cadastrar(@Valid @RequestBody CadastrarExemplarParamsV1 params) {
        return cadastrarExemplarServiceV1.cadastrar(params);
    }
}
