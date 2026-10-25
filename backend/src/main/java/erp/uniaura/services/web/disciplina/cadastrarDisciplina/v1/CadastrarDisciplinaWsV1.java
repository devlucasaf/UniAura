package erp.uniaura.services.web.disciplina.cadastrarDisciplina.v1;

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
public class CadastrarDisciplinaWsV1 extends BaseWs {

    private final CadastrarDisciplinaServiceV1 cadastrarDisciplinaServiceV1;

    // --- ENDPOINT QUE CADASTRA UMA DISCIPLINA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Cadastra uma disciplina",
            description = "Cria a disciplina no curso indicado por 'cursoUid' e devolve o uid gerado. "
                    + "Erro 422 se o código já existir ou se o curso não existir. Sem 'ativo', a disciplina é cadastrada como ativa.")
    public Uid cadastrar(@Valid @RequestBody CadastrarDisciplinaParamsV1 params) {
        return cadastrarDisciplinaServiceV1.cadastrar(params);
    }
}
