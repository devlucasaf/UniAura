package erp.uniaura.services.web.biblioteca.devolverEmprestimo.v1;

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
public class DevolverEmprestimoWsV1 extends BaseWs {

    private final DevolverEmprestimoServiceV1 devolverEmprestimoServiceV1;

    // --- ENDPOINT QUE REGISTRA A DEVOLUÇÃO DO EMPRÉSTIMO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Registra a devolução de um empréstimo",
            description = "Marca o empréstimo como DEVOLVIDO, gera multa se houve atraso e deixa o exemplar RESERVADO (se há fila de reservas) ou DISPONIVEL. "
                    + "O resultado, com atraso e multa, é lido em 'detalharEmprestimo'. Erro 422 se o empréstimo não existir ou já tiver sido devolvido.")
    public void devolver(@Valid @RequestBody DevolverEmprestimoParamsV1 params) {
        devolverEmprestimoServiceV1.devolver(params);
    }
}
