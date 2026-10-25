package erp.uniaura.services.web.turma.adicionarDisciplinaNaTurma.v1;

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
public class AdicionarDisciplinaNaTurmaWsV1 extends BaseWs {

    private final AdicionarDisciplinaNaTurmaServiceV1 adicionarDisciplinaNaTurmaServiceV1;

    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Adiciona uma disciplina a uma turma",
            description = "Vincula a disciplina à turma com professor responsável, dia da semana e horário, e devolve o uid do vínculo. "
                    + "Erro 422 se o horário de término não for depois do início, se a disciplina já estiver na turma, "
                    + "se o professor não estiver habilitado a lecionar a disciplina ou se turma, disciplina ou professor não existirem.")
    public Uid adicionar(@Valid @RequestBody AdicionarDisciplinaNaTurmaParamsV1 params) {
        return adicionarDisciplinaNaTurmaServiceV1.adicionar(params);
    }
}
