package erp.uniaura.services.web.biblioteca.alterarExemplar.v1;

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
public class AlterarExemplarWsV1 extends BaseWs {

    private final AlterarExemplarServiceV1 alterarExemplarServiceV1;

    // --- ENDPOINT QUE ALTERA LOCALIZAÇÃO E STATUS DO EXEMPLAR ---
    @PostMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Altera a localização e o status de um exemplar",
            description = "Atualiza a localização e, se informado, o status administrativo. Erro 422 se o exemplar não existir ou se o status pedido for EMPRESTADO ou RESERVADO.")
    public void alterar(@Valid @RequestBody AlterarExemplarParamsV1 params) {
        alterarExemplarServiceV1.alterar(params);
    }
}
