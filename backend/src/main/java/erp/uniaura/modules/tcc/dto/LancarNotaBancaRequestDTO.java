package erp.uniaura.modules.tcc.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LancarNotaBancaRequestDTO {

    @NotNull(message = "A nota é obrigatória.")
    @DecimalMin(value = "0.00", message = "A nota deve estar entre 0 e 10.")
    @DecimalMax(value = "10.00", message = "A nota deve estar entre 0 e 10.")
    private BigDecimal nota;

    @Size(max = 1000)
    private String parecer;
}
