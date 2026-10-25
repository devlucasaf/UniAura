package erp.uniaura.services.web.coordenacao.consultarCargaHorariaDoProfessor.v1;

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
public class ConsultarCargaHorariaDoProfessorWsV1 extends BaseWs {

    private final ConsultarCargaHorariaDoProfessorServiceV1 consultarCargaHorariaDoProfessorServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN CONSULTAM A CARGA HORÁRIA DOS PROFESSORES ---
    @GetMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Consulta a carga horária semanal de um professor",
            description = "Soma as horas semanais das aulas do professor, no período letivo informado ou em todos, e compara com a carga contratada. "
                    + "Erro 422 se o professor não existir.")
    public ConsultarCargaHorariaDoProfessorRetornoV1 consultar(@Valid @ParameterObject ConsultarCargaHorariaDoProfessorParamsV1 params) {
        return consultarCargaHorariaDoProfessorServiceV1.consultar(params);
    }
}
