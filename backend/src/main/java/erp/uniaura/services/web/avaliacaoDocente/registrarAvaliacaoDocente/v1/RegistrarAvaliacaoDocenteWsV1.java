package erp.uniaura.services.web.avaliacaoDocente.registrarAvaliacaoDocente.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class RegistrarAvaliacaoDocenteWsV1 extends BaseWs {

    private final RegistrarAvaliacaoDocenteServiceV1 registrarAvaliacaoDocenteServiceV1;

    // --- ENDPOINT QUE REGISTRA A AVALIAÇÃO DO ALUNO SOBRE O DOCENTE ---
    @PostMapping
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Registra a avaliação do aluno autenticado sobre o professor de uma turma e disciplina",
            description = "Grava as notas de didática, pontualidade e disponibilidade (1 a 5) e o comentário opcional. Cada aluno avalia uma vez cada vínculo. "
                    + "Erro 422 se o vínculo não existir, se o usuário não for aluno, se não houver matrícula ATIVA na turma ou se o aluno já avaliou essa disciplina.")
    public void registrar(@LoggedInUserId String usuarioId, @Valid @RequestBody RegistrarAvaliacaoDocenteParamsV1 params) {
        registrarAvaliacaoDocenteServiceV1.registrar(Long.valueOf(usuarioId), params);
    }
}
