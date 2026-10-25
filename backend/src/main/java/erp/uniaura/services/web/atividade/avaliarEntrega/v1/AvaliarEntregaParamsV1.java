package erp.uniaura.services.web.atividade.avaliarEntrega.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AvaliarEntregaParamsV1 {

    @Schema(description = "Identificador público da entrega")
    @NotNull(message = "O uid da entrega é obrigatório.")
    private Long entregaUid;

    @Schema(description = "Nota da entrega, de 0 a 10, sem ultrapassar o valor máximo da atividade")
    @NotNull(message = "A nota é obrigatória.")
    @DecimalMin(value = "0.00", message = "A nota mínima é 0.")
    @DecimalMax(value = "10.00", message = "A nota máxima é 10.")
    private BigDecimal nota;

    @Schema(description = "Comentário do professor sobre a entrega")
    @Size(max = 2000, message = "O feedback deve ter no máximo 2000 caracteres.")
    private String feedback;
}
