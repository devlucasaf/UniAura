package erp.uniaura.services.web.tcc.consultarMeuTcc.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class ConsultarMeuTccWsV1 extends BaseWs {

    private final ConsultarMeuTccServiceV1 consultarMeuTccServiceV1;

    @GetMapping
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Consulta o TCC em andamento do aluno autenticado",
            description = "Devolve o TCC que ainda não foi aprovado, reprovado nem cancelado. Erro 422 se o usuário não tiver cadastro de aluno ou não tiver TCC em andamento.")
    public ConsultarMeuTccRetornoV1 consultar(@LoggedInUserId String usuarioId) {
        return consultarMeuTccServiceV1.consultar(Long.valueOf(usuarioId));
    }
}
