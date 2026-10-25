package erp.uniaura.services.web.estagio.pesquisarRelatoriosDoEstagio.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarRelatoriosDoEstagioWsV1 extends BaseWs {

    private final PesquisarRelatoriosDoEstagioServiceV1 pesquisarRelatoriosDoEstagioServiceV1;

    // --- ENDPOINT QUE LISTA OS RELATÓRIOS DE UM ESTÁGIO DE FORMA PAGINADA ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Pesquisa os relatórios de um estágio de forma paginada",
            description = "Lista os relatórios enviados para o estágio. O aluno só consulta os do próprio estágio; a equipe acadêmica consulta de qualquer um. "
                    + "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o estágio não existir ou se o usuário for aluno de outro estágio.")
    public PesquisarRelatoriosDoEstagioRetornoV1 pesquisar(@LoggedInUserId String usuarioId,
                                                           @Valid @ParameterObject PesquisarRelatoriosDoEstagioParamsV1 params) {
        return pesquisarRelatoriosDoEstagioServiceV1.pesquisar(Long.valueOf(usuarioId), params);
    }
}
