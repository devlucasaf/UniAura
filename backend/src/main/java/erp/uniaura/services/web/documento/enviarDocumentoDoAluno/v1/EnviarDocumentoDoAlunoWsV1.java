package erp.uniaura.services.web.documento.enviarDocumentoDoAluno.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.Uid;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Ws
@RequiredArgsConstructor
public class EnviarDocumentoDoAlunoWsV1 extends BaseWs {

    private final EnviarDocumentoDoAlunoServiceV1 enviarDocumentoDoAlunoServiceV1;

    // --- ENDPOINT QUE ENVIA UM DOCUMENTO DO ALUNO ---
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @HasAnyAuthority({"ROLE_ALUNO", "ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Envia um documento do aluno",
            description = "Os dados vão na parte 'dados' (JSON) e o arquivo na parte 'arquivo'. Cria o documento com status PENDENTE e a próxima versão do tipo, e devolve o uid gerado. "
                    + "O aluno envia para si mesmo; a equipe precisa informar 'alunoUid'. Erro 422 se faltar o arquivo, se o aluno não existir ou se o usuário ALUNO não tiver cadastro de aluno.")
    public Uid enviar(@LoggedInUserId String usuarioId,
                      @Valid @RequestPart("dados") EnviarDocumentoDoAlunoParamsV1 params,
                      @RequestPart("arquivo") MultipartFile arquivo) {
        return enviarDocumentoDoAlunoServiceV1.enviar(Long.valueOf(usuarioId), params, arquivo);
    }
}
