package erp.uniaura.services.web.turma.pesquisarTurmas.v1;

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
public class PesquisarTurmasWsV1 extends BaseWs {

    private final PesquisarTurmasServiceV1 pesquisarTurmasServiceV1;

    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa turmas de forma paginada",
            description = "Lista as turmas, com filtros opcionais por código exato, curso ('cursoUid') e período letivo; os filtros informados se combinam. "
                    + "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o curso informado não existir.")
    public PesquisarTurmasRetornoV1 pesquisar(@Valid @ParameterObject PesquisarTurmasParamsV1 params) {
        return pesquisarTurmasServiceV1.pesquisar(params);
    }
}
