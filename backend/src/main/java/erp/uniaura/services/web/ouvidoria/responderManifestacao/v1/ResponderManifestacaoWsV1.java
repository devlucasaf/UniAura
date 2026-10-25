package erp.uniaura.services.web.ouvidoria.responderManifestacao.v1;

import cloudsupport.security.HasAnyAuthority;
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
public class ResponderManifestacaoWsV1 extends BaseWs {

    private final ResponderManifestacaoServiceV1 responderManifestacaoServiceV1;

    // --- ENDPOINT QUE RESPONDE A MANIFESTAÇÃO E MUDA SEU STATUS ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Registra uma resposta e move a manifestação para um novo status",
            description = "Grava a resposta no histórico e muda o status; quem responde passa a ser o responsável pela manifestação. "
                    + "Erro 422 se a manifestação não existir, se já estiver ENCERRADA ou ARQUIVADA, se o status novo for igual ao atual ou se a transição não for permitida.")
    public void responder(@LoggedInUserId String usuarioId, @Valid @RequestBody ResponderManifestacaoParamsV1 params) {
        responderManifestacaoServiceV1.responder(Long.valueOf(usuarioId), params);
    }
}
