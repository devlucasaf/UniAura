package erp.uniaura.services.web.aluno.cadastrarAluno.v1;

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
public class CadastrarAlunoWsV1 extends BaseWs {

    private final CadastrarAlunoServiceV1 cadastrarAlunoServiceV1;

    // --- A EQUIPE ACADÊMICA CADASTRA ALUNOS ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Cadastra um aluno",
            description = "Cria o usuário (perfil ALUNO) e o cadastro acadêmico e devolve o uid do aluno. "
                    + "Erro 422 se a matrícula ou o e-mail já estiverem em uso ou se a turma não existir.")
    public Uid cadastrar(@Valid @RequestBody CadastrarAlunoParamsV1 params) {
        return cadastrarAlunoServiceV1.cadastrar(params);
    }
}
