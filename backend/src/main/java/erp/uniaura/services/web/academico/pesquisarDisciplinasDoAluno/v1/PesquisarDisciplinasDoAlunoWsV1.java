package erp.uniaura.services.web.academico.pesquisarDisciplinasDoAluno.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarDisciplinasDoAlunoWsV1 extends BaseWs {

    private final PesquisarDisciplinasDoAlunoServiceV1 pesquisarDisciplinasDoAlunoServiceV1;

    // --- SÓ O ALUNO CONSULTA A PRÓPRIA MATRIZ, IDENTIFICADO PELO TOKEN ---
    @GetMapping
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Pesquisa a matriz de disciplinas do curso do aluno logado",
            description = "Devolve o curso do aluno (pelo cadastro), os semestres com suas disciplinas e o semestre em que o aluno está. "
                    + "O aluno é identificado pelo token; não há parâmetros. "
                    + "Erro 422 se o aluno não tem curso informado ou se o curso não está no catálogo.")
    public PesquisarDisciplinasDoAlunoRetornoV1 pesquisar(@LoggedInUserId String usuarioId) {
        return pesquisarDisciplinasDoAlunoServiceV1.pesquisar(Long.valueOf(usuarioId));
    }
}
