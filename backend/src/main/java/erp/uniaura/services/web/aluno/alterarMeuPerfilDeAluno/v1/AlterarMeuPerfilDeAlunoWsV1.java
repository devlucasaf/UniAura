package erp.uniaura.services.web.aluno.alterarMeuPerfilDeAluno.v1;

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
public class AlterarMeuPerfilDeAlunoWsV1 extends BaseWs {

    private final AlterarMeuPerfilDeAlunoServiceV1 alterarMeuPerfilDeAlunoServiceV1;

    // --- O ALUNO ALTERA SOMENTE O PRÓPRIO PERFIL ---
    @PostMapping
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Altera os dados pessoais do aluno autenticado",
            description = "Atualiza nome, contato, filiação, endereço, documentos e escolaridade. "
                    + "Erro 422 se o usuário logado não tiver cadastro de aluno.")
    public void alterar(@LoggedInUserId String usuarioId, @Valid @RequestBody AlterarMeuPerfilDeAlunoParamsV1 params) {
        alterarMeuPerfilDeAlunoServiceV1.alterar(Long.valueOf(usuarioId), params);
    }
}