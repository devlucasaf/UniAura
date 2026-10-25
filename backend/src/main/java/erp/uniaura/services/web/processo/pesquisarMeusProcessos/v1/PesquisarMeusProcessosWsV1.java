package erp.uniaura.services.web.processo.pesquisarMeusProcessos.v1;

import cloudsupport.security.HasAuthority;
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
public class PesquisarMeusProcessosWsV1 extends BaseWs {

    private final PesquisarMeusProcessosServiceV1 pesquisarMeusProcessosServiceV1;

    // --- ENDPOINT QUE LISTA OS PROCESSOS DO ALUNO LOGADO ---
    @GetMapping
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Pesquisa os processos do aluno autenticado de forma paginada",
            description = "Lista só os processos do aluno autenticado, filtrando opcionalmente por status. A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o usuário não tiver cadastro de aluno.")
    public PesquisarMeusProcessosRetornoV1 pesquisar(@LoggedInUserId String usuarioId, @Valid @ParameterObject PesquisarMeusProcessosParamsV1 params) {
        return pesquisarMeusProcessosServiceV1.pesquisar(Long.valueOf(usuarioId), params);
    }
}
