package erp.uniaura.services.web.professor.adicionarDisciplinaAoProfessor.v1;

import cloudsupport.security.HasAnyAuthority;
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
public class AdicionarDisciplinaAoProfessorWsV1 extends BaseWs {

    private final AdicionarDisciplinaAoProfessorServiceV1 adicionarDisciplinaAoProfessorServiceV1;

    // --- ENDPOINT QUE HABILITA O PROFESSOR A LECIONAR UMA DISCIPLINA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Habilita um professor a lecionar uma disciplina",
            description = "Vincula a disciplina ao professor e devolve o uid do vínculo. "
                    + "Erro 422 se professor ou disciplina não existirem ou se o vínculo já existir.")
    public Uid adicionar(@Valid @RequestBody AdicionarDisciplinaAoProfessorParamsV1 params) {
        return adicionarDisciplinaAoProfessorServiceV1.adicionar(params);
    }
}
