package erp.uniaura.services.web.matricula.cancelarMatricula.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class CancelarMatriculaWsV1 extends BaseWs {

    private final CancelarMatriculaServiceV1 cancelarMatriculaServiceV1;

    // --- ENDPOINT QUE CANCELA UMA MATRÍCULA ATIVA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Cancela uma matrícula ativa",
            description = "Muda o status para CANCELADA, libera a vaga e tira a turma atual do aluno, se for a mesma. Erro 422 se a matrícula não existir ou não estiver ATIVA.")
    public void cancelar(@Valid @RequestBody CancelarMatriculaParamsV1 params) {
        cancelarMatriculaServiceV1.cancelar(params);
    }
}
