package erp.uniaura.services.web.calendario.pesquisarEventosAcademicos.v1;

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
public class PesquisarEventosAcademicosWsV1 extends BaseWs {

    private final PesquisarEventosAcademicosServiceV1 pesquisarEventosAcademicosServiceV1;

    // --- ENDPOINT QUE LISTA OS EVENTOS ACADÊMICOS DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa eventos do calendário acadêmico de forma paginada",
            description = "Lista os eventos (provas, feriados, matrícula, recessos e eventos institucionais), opcionalmente filtrando por tipo. "
                    + "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). Para o calendário do site, sem login, use pesquisarEventosPublicos.")
    public PesquisarEventosAcademicosRetornoV1 pesquisar(@Valid @ParameterObject PesquisarEventosAcademicosParamsV1 params) {
        return pesquisarEventosAcademicosServiceV1.pesquisar(params);
    }
}
