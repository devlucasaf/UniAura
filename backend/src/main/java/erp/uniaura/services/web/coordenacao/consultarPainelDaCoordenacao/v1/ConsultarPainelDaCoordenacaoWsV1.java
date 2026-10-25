package erp.uniaura.services.web.coordenacao.consultarPainelDaCoordenacao.v1;

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
public class ConsultarPainelDaCoordenacaoWsV1 extends BaseWs {

    private final ConsultarPainelDaCoordenacaoServiceV1 consultarPainelDaCoordenacaoServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN VEEM OS INDICADORES ACADÊMICOS ---
    @GetMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Consolida os indicadores acadêmicos da coordenação",
            description = "Devolve matrículas, turmas, ocupação, desempenho, frequência e pendências, opcionalmente filtrados por curso e período letivo. "
                    + "Erro 422 se o curso informado não existir.")
    public ConsultarPainelDaCoordenacaoRetornoV1 consultar(@Valid @ParameterObject ConsultarPainelDaCoordenacaoParamsV1 params) {
        return consultarPainelDaCoordenacaoServiceV1.consultar(params);
    }
}
