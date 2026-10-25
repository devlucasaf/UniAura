package erp.uniaura.services.web.calendario.pesquisarEventosPublicos.v1;

import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarEventosPublicosWsV1 extends BaseWs {

    private final PesquisarEventosPublicosServiceV1 pesquisarEventosPublicosServiceV1;

    // --- ENDPOINT PÚBLICO QUE LISTA OS EVENTOS DO CALENDÁRIO ---
    @GetMapping
    @Operation(summary = "Pesquisa os eventos públicos do calendário (sem autenticação)",
            description = "Lista os eventos marcados como públicos, do mais próximo ao mais distante, para o calendário do site institucional. "
                    + "Este endpoint é público e está em cloudsupport.security.allow-paths.")
    public PesquisarEventosPublicosRetornoV1 pesquisar() {
        return pesquisarEventosPublicosServiceV1.pesquisar();
    }
}
