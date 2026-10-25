package erp.uniaura.services.web.tcc.agendarDefesaDoTcc.v1;

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
public class AgendarDefesaDoTccWsV1 extends BaseWs {

    private final AgendarDefesaDoTccServiceV1 agendarDefesaDoTccServiceV1;

    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Agenda a defesa de um TCC",
            description = "Grava a data da defesa e muda o status para AGUARDANDO_BANCA. Erro 422 se o TCC não existir ou não estiver EM_ANDAMENTO.")
    public void agendar(@Valid @RequestBody AgendarDefesaDoTccParamsV1 params) {
        agendarDefesaDoTccServiceV1.agendar(params);
    }
}
