package erp.uniaura.services.web.biblioteca.alterarLivro.v1;

import cloudsupport.security.HasAnyAuthority;
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
public class AlterarLivroWsV1 extends BaseWs {

    private final AlterarLivroServiceV1 alterarLivroServiceV1;

    // --- ENDPOINT QUE ALTERA UM LIVRO DO ACERVO E SUA CAPA ---
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Altera um livro do acervo",
            description = "Os dados vão na parte 'dados' (JSON) e a nova capa, opcional, na parte 'capa'; ao trocar a capa, a anterior é apagada. "
                    + "Erro 422 se o livro não existir ou se outro livro já tiver o mesmo ISBN.")
    public void alterar(@Valid @RequestPart("dados") AlterarLivroParamsV1 params,
                        @RequestPart(value = "capa", required = false) MultipartFile capa) {
        alterarLivroServiceV1.alterar(params, capa);
    }
}
