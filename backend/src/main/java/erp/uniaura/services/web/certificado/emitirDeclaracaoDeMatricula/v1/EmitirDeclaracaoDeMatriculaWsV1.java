package erp.uniaura.services.web.certificado.emitirDeclaracaoDeMatricula.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class EmitirDeclaracaoDeMatriculaWsV1 extends BaseWs {

    private final EmitirDeclaracaoDeMatriculaServiceV1 emitirDeclaracaoDeMatriculaServiceV1;

    // --- ENDPOINT QUE EMITE A DECLARAÇÃO DE MATRÍCULA EM PDF ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ALUNO", "ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Emite a declaração de matrícula do aluno em PDF",
            description = "Gera o PDF, registra a emissão com um código de verificação e devolve o arquivo como anexo. "
                    + "O aluno só emite o próprio documento; a equipe emite de qualquer aluno. Erro 422 se o aluno não existir ou se o aluno tentar emitir o documento de outra pessoa.")
    public ResponseEntity<byte[]> emitir(@LoggedInUserId String usuarioId, @Valid @ParameterObject EmitirDeclaracaoDeMatriculaParamsV1 params) {
        byte[] pdf = emitirDeclaracaoDeMatriculaServiceV1.emitir(Long.valueOf(usuarioId), params);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"declaracao-matricula.pdf\"")
                .body(pdf);
    }
}
