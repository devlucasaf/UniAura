package erp.uniaura.services.web.financeiro.detalharMensalidade.v1;

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
public class DetalharMensalidadeWsV1 extends BaseWs {

    private final DetalharMensalidadeServiceV1 detalharMensalidadeServiceV1;

    // --- A EQUIPE FINANCEIRA VÊ QUALQUER MENSALIDADE ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO", "ROLE_SECRETARIA", "ROLE_ALUNO"})
    @Operation(summary = "Detalha uma mensalidade",
            description = "Devolve os dados da mensalidade e, quando já paga, os do pagamento. "
                    + "Erro 422 se a mensalidade não existir ou se o aluno tentar ver a mensalidade de outro aluno.")
    public DetalharMensalidadeRetornoV1 detalhar(@LoggedInUserId String usuarioId, @Valid @ParameterObject DetalharMensalidadeParamsV1 params) {
        return detalharMensalidadeServiceV1.detalhar(Long.valueOf(usuarioId), params);
    }
}
