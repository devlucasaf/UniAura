package erp.uniaura.services.web.coordenacao.detalharReuniaoDoColegiado.v1;

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
public class DetalharReuniaoDoColegiadoWsV1 extends BaseWs {

    private final DetalharReuniaoDoColegiadoServiceV1 detalharReuniaoDoColegiadoServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN CONSULTAM O DETALHE DE UMA REUNIÃO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Detalha uma reunião do colegiado",
            description = "Devolve pauta, deliberações e participantes convocados. Erro 422 se a reunião não existir.")
    public DetalharReuniaoDoColegiadoRetornoV1 detalhar(@Valid @ParameterObject DetalharReuniaoDoColegiadoParamsV1 params) {
        return detalharReuniaoDoColegiadoServiceV1.detalhar(params);
    }
}
