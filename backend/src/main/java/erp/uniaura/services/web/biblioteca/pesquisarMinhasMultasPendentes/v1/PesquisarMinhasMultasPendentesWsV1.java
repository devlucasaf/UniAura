package erp.uniaura.services.web.biblioteca.pesquisarMinhasMultasPendentes.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarMinhasMultasPendentesWsV1 extends BaseWs {

    private final PesquisarMinhasMultasPendentesServiceV1 pesquisarMinhasMultasPendentesServiceV1;

    // --- ENDPOINT QUE LISTA AS MULTAS PENDENTES DO USUÁRIO LOGADO ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Pesquisa as multas pendentes do usuário autenticado",
            description = "Lista todas as multas ainda não pagas do usuário logado, da mais recente para a mais antiga.")
    public PesquisarMinhasMultasPendentesRetornoV1 pesquisar(@LoggedInUserId String usuarioId) {
        return pesquisarMinhasMultasPendentesServiceV1.pesquisar(Long.valueOf(usuarioId));
    }
}
