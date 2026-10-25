package erp.uniaura.services.web.tcc.pesquisarEntregasDoTcc.v1;

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
public class PesquisarEntregasDoTccWsV1 extends BaseWs {

    private final PesquisarEntregasDoTccServiceV1 pesquisarEntregasDoTccServiceV1;

    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Pesquisa as entregas de um TCC de forma paginada",
            description = "O aluno só consulta as entregas do próprio TCC; professores, coordenação e admin consultam de qualquer um. "
                    + "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o TCC não existir ou se o usuário for aluno de outro TCC.")
    public PesquisarEntregasDoTccRetornoV1 pesquisar(@LoggedInUserId String usuarioId,
                                                     @Valid @ParameterObject PesquisarEntregasDoTccParamsV1 params) {
        return pesquisarEntregasDoTccServiceV1.pesquisar(Long.valueOf(usuarioId), params);
    }
}
