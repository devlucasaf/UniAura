package erp.uniaura.services.web.biblioteca.pesquisarMinhasReservas.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarMinhasReservasWsV1 extends BaseWs {

    private final PesquisarMinhasReservasServiceV1 pesquisarMinhasReservasServiceV1;

    // --- ENDPOINT QUE LISTA AS RESERVAS DO USUÁRIO LOGADO ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Pesquisa as reservas do usuário autenticado de forma paginada",
            description = "Lista só as reservas do usuário logado. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo).")
    public PesquisarMinhasReservasRetornoV1 pesquisar(@LoggedInUserId String usuarioId, @Valid @ParameterObject PesquisarMinhasReservasParamsV1 params) {
        return pesquisarMinhasReservasServiceV1.pesquisar(Long.valueOf(usuarioId), params);
    }
}
