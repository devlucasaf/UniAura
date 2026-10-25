package erp.uniaura.services.web.coordenacao.cadastrarPlanoDeEnsino.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.LoggedInUserId;
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
public class CadastrarPlanoDeEnsinoWsV1 extends BaseWs {

    private final CadastrarPlanoDeEnsinoServiceV1 cadastrarPlanoDeEnsinoServiceV1;

    // --- O PROFESSOR RESPONSÁVEL, A COORDENAÇÃO E O ADMIN CRIAM O PLANO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Cadastra o plano de ensino de uma turma e disciplina",
            description = "Cria o plano em RASCUNHO e devolve o uid gerado. "
                    + "Erro 422 se o vínculo não existir, se já houver plano para ele ou se o usuário não for o professor responsável.")
    public Uid cadastrar(@LoggedInUserId String usuarioId, @Valid @RequestBody CadastrarPlanoDeEnsinoParamsV1 params) {
        return cadastrarPlanoDeEnsinoServiceV1.cadastrar(Long.valueOf(usuarioId), params);
    }
}
