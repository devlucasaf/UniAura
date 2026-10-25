package erp.uniaura.services.web.estagio.consultarMeuEstagio.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class ConsultarMeuEstagioWsV1 extends BaseWs {

    private final ConsultarMeuEstagioServiceV1 consultarMeuEstagioServiceV1;

    // --- ENDPOINT QUE RETORNA O ESTÁGIO EM ANDAMENTO DO ALUNO LOGADO ---
    @GetMapping
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Consulta o estágio em andamento do aluno autenticado",
            description = "Devolve o estágio EM_ANDAMENTO do aluno logado. Erro 422 se o usuário não tiver cadastro de aluno ou não tiver estágio em andamento.")
    public ConsultarMeuEstagioRetornoV1 consultar(@LoggedInUserId String usuarioId) {
        return consultarMeuEstagioServiceV1.consultar(Long.valueOf(usuarioId));
    }
}
