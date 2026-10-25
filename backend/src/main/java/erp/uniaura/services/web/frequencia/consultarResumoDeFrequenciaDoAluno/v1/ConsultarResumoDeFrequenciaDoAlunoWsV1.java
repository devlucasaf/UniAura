package erp.uniaura.services.web.frequencia.consultarResumoDeFrequenciaDoAluno.v1;

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
public class ConsultarResumoDeFrequenciaDoAlunoWsV1 extends BaseWs {

    private final ConsultarResumoDeFrequenciaDoAlunoServiceV1 consultarResumoDeFrequenciaDoAlunoServiceV1;

    // --- ENDPOINT QUE CONSULTA O RESUMO DE FREQUÊNCIA DO ALUNO POR DISCIPLINA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Consulta o resumo de frequência do aluno por disciplina",
            description = "Devolve, para cada disciplina, o total de aulas, as presenças, as faltas e o percentual. Erro 422 se o aluno não existir ou se não tiver nenhuma frequência registrada.")
    public ConsultarResumoDeFrequenciaDoAlunoRetornoV1 consultar(@Valid @ParameterObject ConsultarResumoDeFrequenciaDoAlunoParamsV1 params) {
        return consultarResumoDeFrequenciaDoAlunoServiceV1.consultar(params);
    }
}
