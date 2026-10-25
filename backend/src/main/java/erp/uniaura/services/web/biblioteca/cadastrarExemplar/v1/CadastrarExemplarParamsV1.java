package erp.uniaura.services.web.biblioteca.cadastrarExemplar.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarExemplarParamsV1 {

    @Schema(description = "Identificador público do livro")
    @NotNull(message = "O uid do livro é obrigatório.")
    private Long livroUid;

    @Schema(description = "Código de barras; se vazio, é gerado automaticamente")
    @Size(max = 50, message = "O código de barras deve ter no máximo 50 caracteres.")
    private String codigoBarras;

    @Schema(description = "Localização física, como a estante")
    @Size(max = 50, message = "A localização deve ter no máximo 50 caracteres.")
    private String localizacao;
}
