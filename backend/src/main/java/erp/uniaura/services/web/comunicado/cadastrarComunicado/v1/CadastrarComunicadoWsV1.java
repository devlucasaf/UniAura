package erp.uniaura.services.web.comunicado.cadastrarComunicado.v1;

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
public class CadastrarComunicadoWsV1 extends BaseWs {

    private final CadastrarComunicadoServiceV1 cadastrarComunicadoServiceV1;

    // --- ENDPOINT QUE CADASTRA UM COMUNICADO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Cadastra um comunicado",
            description = "Cria o comunicado em nome do usuário do token e devolve o uid gerado. "
                    + "Quando o público-alvo é TURMA, 'turmaUid' é obrigatório. Erro 422 se a turma não existir.")
    public Uid cadastrar(@LoggedInUserId String usuarioId, @Valid @RequestBody CadastrarComunicadoParamsV1 params) {
        return cadastrarComunicadoServiceV1.cadastrar(Long.valueOf(usuarioId), params);
    }
}
