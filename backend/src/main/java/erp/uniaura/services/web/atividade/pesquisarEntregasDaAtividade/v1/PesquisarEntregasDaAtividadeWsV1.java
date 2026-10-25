package erp.uniaura.services.web.atividade.pesquisarEntregasDaAtividade.v1;

import cloudsupport.security.HasAnyAuthority;
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
public class PesquisarEntregasDaAtividadeWsV1 extends BaseWs {

    private final PesquisarEntregasDaAtividadeServiceV1 pesquisarEntregasDaAtividadeServiceV1;

    // --- ENDPOINT QUE LISTA AS ENTREGAS DA ATIVIDADE DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa as entregas dos alunos de uma atividade de forma paginada",
            description = "Lista as entregas da atividade, filtrando opcionalmente por status. Só o professor dono da atividade, a coordenação ou o admin podem consultar. "
                    + "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se a atividade não existir ou se o usuário não for o professor responsável.")
    public PesquisarEntregasDaAtividadeRetornoV1 pesquisar(@LoggedInUserId String usuarioId,
                                                           @Valid @ParameterObject PesquisarEntregasDaAtividadeParamsV1 params) {
        return pesquisarEntregasDaAtividadeServiceV1.pesquisar(Long.valueOf(usuarioId), params);
    }
}
