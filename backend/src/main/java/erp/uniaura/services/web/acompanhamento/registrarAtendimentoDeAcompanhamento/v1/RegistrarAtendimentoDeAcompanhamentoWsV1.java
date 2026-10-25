package erp.uniaura.services.web.acompanhamento.registrarAtendimentoDeAcompanhamento.v1;

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
public class RegistrarAtendimentoDeAcompanhamentoWsV1 extends BaseWs {

    private final RegistrarAtendimentoDeAcompanhamentoServiceV1 registrarAtendimentoDeAcompanhamentoServiceV1;

    // --- SÓ O ADMIN E A COORDENAÇÃO REGISTRAM ATENDIMENTOS, SEMPRE EM NOME DO USUÁRIO AUTENTICADO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Registra um atendimento no acompanhamento",
            description = "Acrescenta ao histórico do acompanhamento um registro (atendimento, adaptação, contato com a família, encaminhamento ou ocorrência) e devolve o uid gerado; "
                    + "o autor é o usuário logado. Erro 422 se o acompanhamento não existir ou estiver encerrado.")
    public Uid registrar(@LoggedInUserId String usuarioId, @Valid @RequestBody RegistrarAtendimentoDeAcompanhamentoParamsV1 params) {
        return registrarAtendimentoDeAcompanhamentoServiceV1.registrar(Long.valueOf(usuarioId), params);
    }
}
