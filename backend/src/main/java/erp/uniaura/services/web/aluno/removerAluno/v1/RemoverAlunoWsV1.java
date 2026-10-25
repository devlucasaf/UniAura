package erp.uniaura.services.web.aluno.removerAluno.v1;

import cloudsupport.security.HasAnyAuthority;
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
public class RemoverAlunoWsV1 extends BaseWs {

    private final RemoverAlunoServiceV1 removerAlunoServiceV1;

    // --- A EQUIPE ACADÊMICA REMOVE ALUNOS ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Remove um aluno",
            description = "Apaga o cadastro acadêmico e registra a exclusão na auditoria. Erro 422 se o aluno não existir; o banco recusa se ele ainda tiver notas, matrículas ou outros vínculos.")
    public void remover(@LoggedInUserId String usuarioId, @Valid @RequestBody RemoverAlunoParamsV1 params) {
        removerAlunoServiceV1.remover(Long.valueOf(usuarioId), params);
    }
}
