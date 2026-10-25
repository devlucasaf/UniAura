package erp.uniaura.services.web.professor.pesquisarProfessores.v1;

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
public class PesquisarProfessoresWsV1 extends BaseWs {

    private final PesquisarProfessoresServiceV1 pesquisarProfessoresServiceV1;

    // --- ENDPOINT QUE LISTA OS PROFESSORES DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa professores de forma paginada",
            description = "Lista os professores, opcionalmente filtrando pela situação ('ativo'). A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo). O retorno traz o total e a página pedida.")
    public PesquisarProfessoresRetornoV1 pesquisar(@Valid @ParameterObject PesquisarProfessoresParamsV1 params) {
        return pesquisarProfessoresServiceV1.pesquisar(params);
    }
}
