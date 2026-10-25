package erp.uniaura.services.web.ouvidoria.detalharManifestacao.v1;

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
public class DetalharManifestacaoWsV1 extends BaseWs {

    private final DetalharManifestacaoServiceV1 detalharManifestacaoServiceV1;

    // --- ENDPOINT QUE DETALHA UMA MANIFESTAÇÃO COM O HISTÓRICO DE RESPOSTAS ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Detalha uma manifestação com o histórico de respostas",
            description = "Busca pelo uid ou pelo número de protocolo. O autor só enxerga as próprias manifestações e não vê as notas internas; "
                    + "a equipe da ouvidoria (ADMIN, COORDENADOR e SECRETARIA) vê todas. "
                    + "Erro 422 se a manifestação não existir, se nenhum dos dois identificadores for informado ou se o usuário não tiver permissão para consultá-la.")
    public DetalharManifestacaoRetornoV1 detalhar(@LoggedInUserId String usuarioId, @Valid @ParameterObject DetalharManifestacaoParamsV1 params) {
        return detalharManifestacaoServiceV1.detalhar(Long.valueOf(usuarioId), params);
    }
}
