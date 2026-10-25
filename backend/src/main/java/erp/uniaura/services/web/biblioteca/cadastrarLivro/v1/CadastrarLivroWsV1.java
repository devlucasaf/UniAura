package erp.uniaura.services.web.biblioteca.cadastrarLivro.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.Uid;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Ws
@RequiredArgsConstructor
public class CadastrarLivroWsV1 extends BaseWs {

    private final CadastrarLivroServiceV1 cadastrarLivroServiceV1;

    // --- ENDPOINT QUE CADASTRA UM LIVRO NO ACERVO COM CAPA OPCIONAL ---
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Cadastra um livro no acervo",
            description = "Os dados vão na parte 'dados' (JSON) e a capa, opcional, na parte 'capa'. Devolve o uid gerado. Erro 422 se já existir livro com o mesmo ISBN.")
    public Uid cadastrar(@Valid @RequestPart("dados") CadastrarLivroParamsV1 params,
                         @RequestPart(value = "capa", required = false) MultipartFile capa) {
        return cadastrarLivroServiceV1.cadastrar(params, capa);
    }
}
