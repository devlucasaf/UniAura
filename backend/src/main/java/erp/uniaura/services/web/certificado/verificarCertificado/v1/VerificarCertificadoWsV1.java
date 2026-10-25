package erp.uniaura.services.web.certificado.verificarCertificado.v1;

import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class VerificarCertificadoWsV1 extends BaseWs {

    private final VerificarCertificadoServiceV1 verificarCertificadoServiceV1;

    // --- ENDPOINT PÚBLICO QUE VERIFICA A AUTENTICIDADE DO CERTIFICADO ---
    @GetMapping
    @Operation(summary = "Verifica a autenticidade de um certificado pelo código impresso no PDF",
            description = "Endpoint público. Devolve o nome do aluno, o tipo, o emissor e a data da emissão. Erro 422 se o código não existir.")
    public VerificarCertificadoRetornoV1 verificar(@Valid @ParameterObject VerificarCertificadoParamsV1 params) {
        return verificarCertificadoServiceV1.verificar(params);
    }
}
