package erp.uniaura.services.web.avaliacaoDocente.consultarMeuDesempenho.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class ConsultarMeuDesempenhoWsV1 extends BaseWs {

    private final ConsultarMeuDesempenhoServiceV1 consultarMeuDesempenhoServiceV1;

    // --- ENDPOINT QUE RETORNA O DESEMPENHO DO PROFESSOR LOGADO ---
    @GetMapping
    @HasAuthority("ROLE_PROFESSOR")
    @Operation(summary = "Consulta a média consolidada do professor autenticado",
            description = "Considera todas as turmas e disciplinas do professor autenticado. Erro 422 se o usuário não tiver vínculo de professor.")
    public ConsultarMeuDesempenhoRetornoV1 consultar(@LoggedInUserId String usuarioId) {
        return consultarMeuDesempenhoServiceV1.consultar(Long.valueOf(usuarioId));
    }
}
