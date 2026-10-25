package erp.uniaura.services.web.financeiro.gerarPixDaMensalidade.v1;

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
public class GerarPixDaMensalidadeWsV1 extends BaseWs {

    private final GerarPixDaMensalidadeServiceV1 gerarPixDaMensalidadeServiceV1;

    // --- O PRÓPRIO ALUNO OU A EQUIPE FINANCEIRA GERAM A COBRANÇA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO", "ROLE_SECRETARIA", "ROLE_ALUNO"})
    @Operation(summary = "Gera o Pix de uma mensalidade",
            description = "Devolve um código Pix copia e cola fictício, com o valor já acrescido da multa se estiver atrasada, válido por 30 minutos. "
                    + "Erro 422 se a mensalidade não existir, se for de outro aluno ou se estiver paga ou cancelada.")
    public GerarPixDaMensalidadeRetornoV1 gerar(@LoggedInUserId String usuarioId, @Valid @ParameterObject GerarPixDaMensalidadeParamsV1 params) {
        return gerarPixDaMensalidadeServiceV1.gerar(Long.valueOf(usuarioId), params);
    }
}
