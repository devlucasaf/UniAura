package erp.uniaura.services.web.turma.cadastrarTurma.v1;

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
public class CadastrarTurmaWsV1 extends BaseWs {

    private final CadastrarTurmaServiceV1 cadastrarTurmaServiceV1;

    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Cadastra uma turma",
            description = "Cria a turma no curso indicado por 'cursoUid' e devolve o uid gerado. "
                    + "Erro 422 se o código já existir, se o curso ou o professor regente não existirem. Sem 'ativa', a turma é cadastrada como ativa.")
    public Uid cadastrar(@Valid @RequestBody CadastrarTurmaParamsV1 params) {
        return cadastrarTurmaServiceV1.cadastrar(params);
    }
}
