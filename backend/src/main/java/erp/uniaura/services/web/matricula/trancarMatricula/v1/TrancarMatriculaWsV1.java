package erp.uniaura.services.web.matricula.trancarMatricula.v1;

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
public class TrancarMatriculaWsV1 extends BaseWs {

    private final TrancarMatriculaServiceV1 trancarMatriculaServiceV1;

    // --- ENDPOINT QUE TRANCA UMA MATRÍCULA ATIVA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Tranca uma matrícula ativa",
            description = "Muda o status para TRANCADA, libera a vaga e tira a turma atual do aluno, se for a mesma. Erro 422 se a matrícula não existir ou não estiver ATIVA.")
    public void trancar(@Valid @RequestBody TrancarMatriculaParamsV1 params) {
        trancarMatriculaServiceV1.trancar(params);
    }
}
