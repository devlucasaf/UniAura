package erp.uniaura.services.web.nota.consultarBoletim.v1;

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
public class ConsultarBoletimWsV1 extends BaseWs {

    private final ConsultarBoletimServiceV1 consultarBoletimServiceV1;

    // --- ENDPOINT QUE CONSULTA O BOLETIM DO ALUNO NO PERÍODO LETIVO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Consulta o boletim do aluno em um período letivo",
            description = "Agrupa as notas do aluno por disciplina e calcula a média ponderada de cada uma. Erro 422 se o aluno não existir.")
    public ConsultarBoletimRetornoV1 consultar(@Valid @ParameterObject ConsultarBoletimParamsV1 params) {
        return consultarBoletimServiceV1.consultar(params);
    }
}
