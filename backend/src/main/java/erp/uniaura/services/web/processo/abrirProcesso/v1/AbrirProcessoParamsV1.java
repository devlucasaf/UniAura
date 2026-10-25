package erp.uniaura.services.web.processo.abrirProcesso.v1;

import erp.uniaura.persistence.entities.TipoProcesso;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbrirProcessoParamsV1 {

    @Schema(description = "Tipo do requerimento")
    @NotNull(message = "O tipo do processo é obrigatório.")
    private TipoProcesso tipo;

    @Schema(description = "Assunto resumido")
    @NotNull(message = "O assunto é obrigatório.")
    @Size(min = 5, max = 200, message = "O assunto deve ter entre 5 e 200 caracteres.")
    private String assunto;

    @Schema(description = "Descrição completa do requerimento")
    @NotNull(message = "A descrição é obrigatória.")
    @Size(min = 20, max = 4000, message = "A descrição deve ter entre 20 e 4000 caracteres.")
    private String descricao;
}
