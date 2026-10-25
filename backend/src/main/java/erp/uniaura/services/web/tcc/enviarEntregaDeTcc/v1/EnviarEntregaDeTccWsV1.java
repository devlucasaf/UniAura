package erp.uniaura.services.web.tcc.enviarEntregaDeTcc.v1;

import cloudsupport.security.IsAuthenticated;
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
public class EnviarEntregaDeTccWsV1 extends BaseWs {

    private final EnviarEntregaDeTccServiceV1 enviarEntregaDeTccServiceV1;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @IsAuthenticated
    @Operation(summary = "Envia uma entrega do TCC (projeto, parcial ou versão final)",
            description = "Os dados vão na parte 'dados' (JSON) e o arquivo na parte 'arquivo'. Cria a entrega PENDENTE e devolve o uid gerado. "
                    + "O aluno envia para o próprio TCC; professores, coordenação e admin enviam para qualquer um. "
                    + "Erro 422 se faltar o arquivo, se o TCC não existir ou não estiver EM_ANDAMENTO, ou se o usuário for aluno de outro TCC.")
    public Uid enviar(@LoggedInUserId String usuarioId,
                      @Valid @RequestPart("dados") EnviarEntregaDeTccParamsV1 params,
                      @RequestPart("arquivo") MultipartFile arquivo) {
        return enviarEntregaDeTccServiceV1.enviar(Long.valueOf(usuarioId), params, arquivo);
    }
}
