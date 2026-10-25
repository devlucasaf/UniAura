package erp.uniaura.services.web.atividade.pesquisarEntregasDoAluno.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarEntregasDoAlunoWsV1 extends BaseWs {

    private final PesquisarEntregasDoAlunoServiceV1 pesquisarEntregasDoAlunoServiceV1;

    // --- ENDPOINT QUE LISTA AS ENTREGAS DE UM ALUNO DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR", "ROLE_ALUNO", "ROLE_RESPONSAVEL"})
    @Operation(summary = "Pesquisa as entregas de atividades de um aluno de forma paginada",
            description = "Lista as entregas do aluno, filtrando opcionalmente por status. O aluno só consulta as próprias entregas e o responsável só as dos alunos vinculados a ele; "
                    + "a equipe e os professores consultam de qualquer aluno. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se o aluno não existir ou se o usuário não tiver permissão para consultá-lo.")
    public PesquisarEntregasDoAlunoRetornoV1 pesquisar(@LoggedInUserId String usuarioId, @Valid @ParameterObject PesquisarEntregasDoAlunoParamsV1 params) {
        return pesquisarEntregasDoAlunoServiceV1.pesquisar(Long.valueOf(usuarioId), params);
    }
}
