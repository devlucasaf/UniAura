package erp.uniaura.services.web.certificado.verificarCertificado.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificarCertificadoParamsV1 {

    @Schema(description = "Código de verificação impresso no rodapé do PDF")
    @NotBlank(message = "O código de verificação é obrigatório.")
    private String codigo;
}
