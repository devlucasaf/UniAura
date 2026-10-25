package erp.uniaura.services.web.acompanhamento.cadastrarAcompanhamentoEspecial.v1;

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
public class CadastrarAcompanhamentoEspecialWsV1 extends BaseWs {

    private final CadastrarAcompanhamentoEspecialServiceV1 cadastrarAcompanhamentoEspecialServiceV1;

    // --- SÓ O ADMIN E A COORDENAÇÃO ABREM UM ACOMPANHAMENTO, E QUEM ABRE FICA COMO RESPONSÁVEL ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Abre o acompanhamento de um aluno especial",
            description = "Cria o acompanhamento com status ATIVO e devolve o uid gerado; quem abre fica como responsável pelo acompanhamento. "
                    + "Erro 422 se o aluno não existir ou se já houver acompanhamento em andamento (ATIVO ou SUSPENSO) do mesmo aluno para esse tipo de necessidade.")
    public Uid cadastrar(@LoggedInUserId String usuarioId, @Valid @RequestBody CadastrarAcompanhamentoEspecialParamsV1 params) {
        return cadastrarAcompanhamentoEspecialServiceV1.cadastrar(Long.valueOf(usuarioId), params);
    }
}
