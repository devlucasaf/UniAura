package erp.uniaura.services.web.documento.removerDocumento.v1;

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
public class RemoverDocumentoWsV1 extends BaseWs {

    private final RemoverDocumentoServiceV1 removerDocumentoServiceV1;

    // --- ENDPOINT QUE REMOVE UM DOCUMENTO DO ALUNO ---
    @PostMapping
    @IsAuthenticated
    @Operation(summary = "Remove um documento do aluno",
            description = "Apaga o registro e o arquivo. A equipe remove qualquer documento; o aluno só remove os próprios, e apenas enquanto estiverem PENDENTES. "
                    + "Erro 422 se o documento não existir, se for de outro aluno ou se já tiver sido analisado.")
    public void remover(@LoggedInUserId String usuarioId, @Valid @RequestBody RemoverDocumentoParamsV1 params) {
        removerDocumentoServiceV1.remover(Long.valueOf(usuarioId), params);
    }
}
