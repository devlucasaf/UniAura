package erp.uniaura.services.web.estagio.encerrarEstagio.v1;

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
public class EncerrarEstagioWsV1 extends BaseWs {

    private final EncerrarEstagioServiceV1 encerrarEstagioServiceV1;

    // --- ENDPOINT QUE ENCERRA UM ESTÁGIO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Encerra um estágio",
            description = "Muda o status para CONCLUIDO, TRANCADO ou CANCELADO e grava a data de fim efetiva. "
                    + "Erro 422 se o estágio não existir, se já estiver CONCLUIDO ou CANCELADO ou se o status pedido for EM_ANDAMENTO.")
    public void encerrar(@Valid @RequestBody EncerrarEstagioParamsV1 params) {
        encerrarEstagioServiceV1.encerrar(params);
    }
}
