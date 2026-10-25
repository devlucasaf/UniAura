package erp.uniaura.services.web.documento.analisarDocumento.v1;

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
public class AnalisarDocumentoWsV1 extends BaseWs {

    private final AnalisarDocumentoServiceV1 analisarDocumentoServiceV1;

    // --- ENDPOINT QUE ANALISA UM DOCUMENTO DO ALUNO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Aprova ou rejeita um documento enviado pelo aluno",
            description = "Grava o resultado, as observações, quem analisou e quando. Erro 422 se o documento não existir.")
    public void analisar(@LoggedInUserId String usuarioId, @Valid @RequestBody AnalisarDocumentoParamsV1 params) {
        analisarDocumentoServiceV1.analisar(Long.valueOf(usuarioId), params);
    }
}
