package erp.uniaura.services.web.ouvidoria.registrarManifestacao.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class RegistrarManifestacaoWsV1 extends BaseWs {

    private final RegistrarManifestacaoServiceV1 registrarManifestacaoServiceV1;

    // --- ENDPOINT QUE REGISTRA UMA MANIFESTAÇÃO DO USUÁRIO LOGADO ---
    @PostMapping
    @IsAuthenticated
    @Operation(summary = "Registra uma manifestação em nome do usuário autenticado",
            description = "Qualquer usuário autenticado pode se manifestar. Cria a manifestação com status ABERTA e devolve o uid e o número de protocolo. "
                    + "Erro 422 se não for possível gerar um protocolo único.")
    public RegistrarManifestacaoRetornoV1 registrar(@LoggedInUserId String usuarioId, @Valid @RequestBody RegistrarManifestacaoParamsV1 params) {
        return registrarManifestacaoServiceV1.registrar(Long.valueOf(usuarioId), params);
    }
}
