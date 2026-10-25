package erp.uniaura.services.web.financeiro.gerarBoletoDaMensalidade.v1;

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
public class GerarBoletoDaMensalidadeWsV1 extends BaseWs {

    private final GerarBoletoDaMensalidadeServiceV1 gerarBoletoDaMensalidadeServiceV1;

    // --- O PRÓPRIO ALUNO OU A EQUIPE FINANCEIRA GERAM A COBRANÇA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO", "ROLE_SECRETARIA", "ROLE_ALUNO"})
    @Operation(summary = "Gera o boleto de uma mensalidade",
            description = "Devolve a linha digitável e o código de barras fictícios, com o valor já acrescido da multa se estiver atrasada. "
                    + "Erro 422 se a mensalidade não existir, se for de outro aluno ou se estiver paga ou cancelada.")
    public GerarBoletoDaMensalidadeRetornoV1 gerar(@LoggedInUserId String usuarioId, @Valid @ParameterObject GerarBoletoDaMensalidadeParamsV1 params) {
        return gerarBoletoDaMensalidadeServiceV1.gerar(Long.valueOf(usuarioId), params);
    }
}
