package erp.uniaura.services.web.frequencia.pesquisarFrequenciasDaAula.v1;

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
public class PesquisarFrequenciasDaAulaWsV1 extends BaseWs {

    private final PesquisarFrequenciasDaAulaServiceV1 pesquisarFrequenciasDaAulaServiceV1;

    // --- ENDPOINT QUE LISTA OS REGISTROS DE PRESENÇA E FALTA DA AULA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa os registros de presença e falta de uma aula",
            description = "Lista a frequência de cada aluno na aula. A paginação usa 'de' e 'qtd' (padrão 100, para a lista de chamada caber numa página); a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se a aula não existir.")
    public PesquisarFrequenciasDaAulaRetornoV1 pesquisar(@Valid @ParameterObject PesquisarFrequenciasDaAulaParamsV1 params) {
        return pesquisarFrequenciasDaAulaServiceV1.pesquisar(params);
    }
}
