package erp.uniaura.services.web.autenticacao.consultarUsuarioLogado.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class ConsultarUsuarioLogadoWsV1 extends BaseWs {

    private final ConsultarUsuarioLogadoServiceV1 consultarUsuarioLogadoServiceV1;

    // --- QUALQUER USUÁRIO LOGADO CONSULTA OS PRÓPRIOS DADOS ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Consulta os dados do usuário autenticado",
            description = "Devolve nome, e-mail, documento, contato e perfil do usuário logado, sem a senha.")
    public ConsultarUsuarioLogadoRetornoV1 consultar(@LoggedInUserId String usuarioId) {
        return consultarUsuarioLogadoServiceV1.consultar(Long.valueOf(usuarioId));
    }
}
