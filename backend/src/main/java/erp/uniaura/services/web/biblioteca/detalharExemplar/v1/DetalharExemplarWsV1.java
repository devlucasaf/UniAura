package erp.uniaura.services.web.biblioteca.detalharExemplar.v1;

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
public class DetalharExemplarWsV1 extends BaseWs {

    private final DetalharExemplarServiceV1 detalharExemplarServiceV1;

    // --- ENDPOINT QUE DETALHA UM EXEMPLAR PELO UID OU CÓDIGO DE BARRAS ---
    @GetMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Detalha um exemplar",
            description = "Busca pelo uid ou pelo código de barras. Erro 422 se o exemplar não existir ou se nenhum dos dois identificadores for informado.")
    public DetalharExemplarRetornoV1 detalhar(@Valid @ParameterObject DetalharExemplarParamsV1 params) {
        return detalharExemplarServiceV1.detalhar(params);
    }
}
