package erp.uniaura.services.web.estagio.enviarRelatorioDeEstagio.v1;

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
public class EnviarRelatorioDeEstagioWsV1 extends BaseWs {

    private final EnviarRelatorioDeEstagioServiceV1 enviarRelatorioDeEstagioServiceV1;

    // --- ENDPOINT QUE ENVIA UM RELATÓRIO PERIÓDICO DE ESTÁGIO ---
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @IsAuthenticated
    @Operation(summary = "Envia um relatório periódico de horas cumpridas no estágio",
            description = "Os dados vão na parte 'dados' (JSON) e o comprovante, opcional, na parte 'comprovante'. Cria o relatório PENDENTE e devolve o uid gerado. "
                    + "O aluno envia para o próprio estágio; a equipe acadêmica envia para qualquer um. "
                    + "Erro 422 se o estágio não existir, não estiver EM_ANDAMENTO, se o usuário for aluno de outro estágio ou se já houver relatório do mesmo período.")
    public Uid enviar(@LoggedInUserId String usuarioId,
                      @Valid @RequestPart("dados") EnviarRelatorioDeEstagioParamsV1 params,
                      @RequestPart(value = "comprovante", required = false) MultipartFile comprovante) {
        return enviarRelatorioDeEstagioServiceV1.enviar(Long.valueOf(usuarioId), params, comprovante);
    }
}
