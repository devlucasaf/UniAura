package erp.uniaura.services.web.biblioteca.removerExemplar.v1;

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
public class RemoverExemplarWsV1 extends BaseWs {

    private final RemoverExemplarServiceV1 removerExemplarServiceV1;

    // --- ENDPOINT QUE REMOVE UM EXEMPLAR ---
    @PostMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Remove um exemplar",
            description = "Erro 422 se o exemplar não existir ou se estiver EMPRESTADO ou RESERVADO.")
    public void remover(@Valid @RequestBody RemoverExemplarParamsV1 params) {
        removerExemplarServiceV1.remover(params);
    }
}
