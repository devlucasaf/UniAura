package erp.uniaura.services.web.atividade.cadastrarAtividade.v1;

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
public class CadastrarAtividadeWsV1 extends BaseWs {

    private final CadastrarAtividadeServiceV1 cadastrarAtividadeServiceV1;

    // --- O PROFESSOR, A COORDENAÇÃO E O ADMIN POSTAM UMA ATIVIDADE ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Posta uma nova atividade",
            description = "Cria a atividade na turma e disciplina do vínculo informado e devolve o uid gerado. Só o professor responsável pela disciplina, a coordenação ou o admin podem postar. "
                    + "Erro 422 se o vínculo não existir, se não tiver professor responsável, se o usuário não for o professor responsável ou se a data de entrega for anterior ao momento do cadastro.")
    public Uid cadastrar(@LoggedInUserId String usuarioId, @Valid @RequestBody CadastrarAtividadeParamsV1 params) {
        return cadastrarAtividadeServiceV1.cadastrar(Long.valueOf(usuarioId), params);
    }
}
