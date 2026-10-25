package erp.uniaura.services.web.tcc.pesquisarTccs.v1;

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
public class PesquisarTccsWsV1 extends BaseWs {

    private final PesquisarTccsServiceV1 pesquisarTccsServiceV1;

    @GetMapping
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa TCCs de forma paginada",
            description = "Lista os TCCs, filtrando opcionalmente por aluno, orientador e status (os filtros se combinam). A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se o aluno ou o orientador do filtro não existirem.")
    public PesquisarTccsRetornoV1 pesquisar(@Valid @ParameterObject PesquisarTccsParamsV1 params) {
        return pesquisarTccsServiceV1.pesquisar(params);
    }
}
