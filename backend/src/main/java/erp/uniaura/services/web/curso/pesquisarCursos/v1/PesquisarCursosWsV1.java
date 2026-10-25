package erp.uniaura.services.web.curso.pesquisarCursos.v1;

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
public class PesquisarCursosWsV1 extends BaseWs {

    private final PesquisarCursosServiceV1 pesquisarCursosServiceV1;

    // --- ENDPOINT QUE LISTA OS CURSOS DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa cursos de forma paginada",
            description = "Lista os cursos, opcionalmente filtrando por nível. A paginação usa 'de' (posição inicial) e 'qtd' (quantidade); "
                    + "a ordenação usa 'ordem' (+campo ou -campo). O retorno traz o total de cursos e a página pedida.")
    public PesquisarCursosRetornoV1 pesquisar(@Valid @ParameterObject PesquisarCursosParamsV1 params) {
        return pesquisarCursosServiceV1.pesquisar(params);
    }
}
