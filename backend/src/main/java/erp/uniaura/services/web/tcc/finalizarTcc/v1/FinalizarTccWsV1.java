package erp.uniaura.services.web.tcc.finalizarTcc.v1;

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
public class FinalizarTccWsV1 extends BaseWs {

    private final FinalizarTccServiceV1 finalizarTccServiceV1;

    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Finaliza um TCC com base nas notas da banca",
            description = "Calcula a média das notas lançadas pela banca, grava como nota final e muda o status para APROVADO (média a partir de 6) ou REPROVADO. "
                    + "Erro 422 se o TCC não existir, não estiver AGUARDANDO_BANCA ou se nenhum membro da banca tiver lançado nota.")
    public void finalizar(@Valid @RequestBody FinalizarTccParamsV1 params) {
        finalizarTccServiceV1.finalizar(params);
    }
}
