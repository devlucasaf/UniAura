package erp.uniaura.services.web.coordenacao.pesquisarMeusPlanosDeEnsino.v1;

import cloudsupport.security.HasAuthority;
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
public class PesquisarMeusPlanosDeEnsinoWsV1 extends BaseWs {

    private final PesquisarMeusPlanosDeEnsinoServiceV1 pesquisarMeusPlanosDeEnsinoServiceV1;

    // --- O PROFESSOR LISTA SOMENTE OS PRÓPRIOS PLANOS ---
    @GetMapping
    @HasAuthority("ROLE_PROFESSOR")
    @Operation(summary = "Pesquisa os planos de ensino do professor autenticado de forma paginada",
            description = "Lista os planos das turmas e disciplinas do professor logado. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se o usuário logado não tiver cadastro de professor.")
    public PesquisarMeusPlanosDeEnsinoRetornoV1 pesquisar(@LoggedInUserId String usuarioId, @Valid @ParameterObject PesquisarMeusPlanosDeEnsinoParamsV1 params) {
        return pesquisarMeusPlanosDeEnsinoServiceV1.pesquisar(Long.valueOf(usuarioId), params);
    }
}
