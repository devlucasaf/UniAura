package erp.uniaura.services.web.tcc.convocarMembroDaBanca.v1;

import cloudsupport.security.HasAnyAuthority;
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
public class ConvocarMembroDaBancaWsV1 extends BaseWs {

    private final ConvocarMembroDaBancaServiceV1 convocarMembroDaBancaServiceV1;

    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Convoca um professor para compor a banca do TCC",
            description = "Cria o membro da banca e devolve o uid gerado. Erro 422 se o TCC ou o professor não existirem ou se o professor já compuser a banca deste TCC.")
    public Uid convocar(@Valid @RequestBody ConvocarMembroDaBancaParamsV1 params) {
        return convocarMembroDaBancaServiceV1.convocar(params);
    }
}
