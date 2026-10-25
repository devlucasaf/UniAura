package erp.uniaura.services.web.tcc.lancarNotaDaBanca.v1;

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
public class LancarNotaDaBancaParamsV1 {

    @Schema(description = "Identificador público do membro da banca")
    @NotNull(message = "O uid do membro da banca é obrigatório.")
    private Long membroUid;

    @Schema(description = "Nota do membro, de 0 a 10")
    @NotNull(message = "A nota é obrigatória.")
    @DecimalMin(value = "0.00", message = "A nota deve estar entre 0 e 10.")
    @DecimalMax(value = "10.00", message = "A nota deve estar entre 0 e 10.")
    private BigDecimal nota;

    @Schema(description = "Parecer do membro sobre o trabalho")
    @Size(max = 1000, message = "O parecer deve ter no máximo 1000 caracteres.")
    private String parecer;
}
