package erp.uniaura.services.web.biblioteca.pesquisarFilaDeReservasDoLivro.v1;

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
public class PesquisarFilaDeReservasDoLivroWsV1 extends BaseWs {

    private final PesquisarFilaDeReservasDoLivroServiceV1 pesquisarFilaDeReservasDoLivroServiceV1;

    // --- ENDPOINT QUE LISTA A FILA DE RESERVAS ATIVAS DO LIVRO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Pesquisa a fila de reservas ativas de um livro",
            description = "Lista as reservas aguardando, na ordem da fila. A paginação usa 'de' e 'qtd'. Erro 422 se o livro não existir.")
    public PesquisarFilaDeReservasDoLivroRetornoV1 pesquisar(@Valid @ParameterObject PesquisarFilaDeReservasDoLivroParamsV1 params) {
        return pesquisarFilaDeReservasDoLivroServiceV1.pesquisar(params);
    }
}
