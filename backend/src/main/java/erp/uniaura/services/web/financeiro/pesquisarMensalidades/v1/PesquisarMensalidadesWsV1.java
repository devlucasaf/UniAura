package erp.uniaura.services.web.financeiro.pesquisarMensalidades.v1;

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
public class PesquisarMensalidadesWsV1 extends BaseWs {

    private final PesquisarMensalidadesServiceV1 pesquisarMensalidadesServiceV1;

    // --- SÓ A EQUIPE FINANCEIRA LISTA AS MENSALIDADES DE TODOS OS ALUNOS ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO", "ROLE_SECRETARIA"})
    @Operation(summary = "Pesquisa mensalidades por aluno e status de forma paginada",
            description = "Lista as mensalidades, filtrando opcionalmente pelo aluno e pelo status. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se o aluno informado não existir.")
    public PesquisarMensalidadesRetornoV1 pesquisar(@Valid @ParameterObject PesquisarMensalidadesParamsV1 params) {
        return pesquisarMensalidadesServiceV1.pesquisar(params);
    }
}
