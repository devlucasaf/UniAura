package erp.uniaura.services.web.processo.detalharProcesso.v1;

import cloudsupport.security.HasAnyAuthority;
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
public class DetalharProcessoWsV1 extends BaseWs {

    private final DetalharProcessoServiceV1 detalharProcessoServiceV1;

    // --- ENDPOINT QUE DETALHA UM PROCESSO COM A LINHA DO TEMPO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_ALUNO"})
    @Operation(summary = "Detalha um processo com a linha do tempo",
            description = "Busca pelo uid ou pelo número de protocolo. O aluno só enxerga os próprios processos e não vê os despachos internos; "
                    + "a equipe (ADMIN, COORDENADOR e SECRETARIA) vê todos. "
                    + "Erro 422 se o processo não existir, se nenhum dos dois identificadores for informado ou se o usuário não tiver permissão para consultá-lo.")
    public DetalharProcessoRetornoV1 detalhar(@LoggedInUserId String usuarioId, @Valid @ParameterObject DetalharProcessoParamsV1 params) {
        return detalharProcessoServiceV1.detalhar(Long.valueOf(usuarioId), params);
    }
}
