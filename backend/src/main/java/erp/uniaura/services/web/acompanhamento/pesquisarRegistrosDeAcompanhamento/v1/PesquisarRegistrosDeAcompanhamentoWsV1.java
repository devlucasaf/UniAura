package erp.uniaura.services.web.acompanhamento.pesquisarRegistrosDeAcompanhamento.v1;

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
public class PesquisarRegistrosDeAcompanhamentoWsV1 extends BaseWs {

    private final PesquisarRegistrosDeAcompanhamentoServiceV1 pesquisarRegistrosDeAcompanhamentoServiceV1;

    // --- SÓ O ADMIN E A COORDENAÇÃO CONSULTAM O HISTÓRICO DE UM ACOMPANHAMENTO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Pesquisa o histórico de um acompanhamento de forma paginada",
            description = "Lista os registros do acompanhamento, filtrando opcionalmente por tipo. A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o acompanhamento não existir.")
    public PesquisarRegistrosDeAcompanhamentoRetornoV1 pesquisar(@Valid @ParameterObject PesquisarRegistrosDeAcompanhamentoParamsV1 params) {
        return pesquisarRegistrosDeAcompanhamentoServiceV1.pesquisar(params);
    }
}
