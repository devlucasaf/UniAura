package erp.uniaura.services.web.matricula.matricularAluno.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.Uid;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class MatricularAlunoWsV1 extends BaseWs {

    private final MatricularAlunoServiceV1 matricularAlunoServiceV1;

    // --- ENDPOINT QUE MATRICULA UM ALUNO EM UMA TURMA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Matricula um aluno em uma turma",
            description = "Cria a matrícula ATIVA, registra quem a fez, define a turma como a atual do aluno e devolve o uid gerado. "
                    + "Erro 422 se o aluno ou a turma não existirem, se a turma estiver inativa, se o aluno já tiver matrícula ATIVA no mesmo período letivo ou se a turma estiver lotada.")
    public Uid matricular(@LoggedInUserId String usuarioId, @Valid @RequestBody MatricularAlunoParamsV1 params) {
        return matricularAlunoServiceV1.matricular(Long.valueOf(usuarioId), params);
    }
}
