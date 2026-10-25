package erp.uniaura.services.web.acompanhamento.alterarAcompanhamentoEspecial.v1;

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
public class AlterarAcompanhamentoEspecialWsV1 extends BaseWs {

    private final AlterarAcompanhamentoEspecialServiceV1 alterarAcompanhamentoEspecialServiceV1;

    // --- SÓ O ADMIN E A COORDENAÇÃO ALTERAM UM ACOMPANHAMENTO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Altera o acompanhamento de um aluno especial",
            description = "Atualiza os dados e o status do acompanhamento; ao encerrar, a data de encerramento passa a ser a de hoje. "
                    + "Erro 422 se o acompanhamento não existir ou se já estiver encerrado.")
    public void alterar(@Valid @RequestBody AlterarAcompanhamentoEspecialParamsV1 params) {
        alterarAcompanhamentoEspecialServiceV1.alterar(params);
    }
}
