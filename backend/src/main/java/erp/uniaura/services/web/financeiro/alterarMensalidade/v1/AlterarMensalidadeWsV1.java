package erp.uniaura.services.web.financeiro.alterarMensalidade.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class AlterarMensalidadeWsV1 extends BaseWs {

    private final AlterarMensalidadeServiceV1 alterarMensalidadeServiceV1;

    // --- SÓ A EQUIPE FINANCEIRA E O ADMIN ALTERAM MENSALIDADES ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO"})
    @Operation(summary = "Altera uma mensalidade",
            description = "Atualiza aluno, competência, valor, vencimento, descrição e, se informado, o status. "
                    + "Erro 422 se a mensalidade ou o aluno não existirem ou se a mensalidade já estiver paga.")
    public void alterar(@Valid @RequestBody AlterarMensalidadeParamsV1 params) {
        alterarMensalidadeServiceV1.alterar(params);
    }
}
