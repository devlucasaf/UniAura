package erp.uniaura.modules.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoletoResponseDTO {

    private String          linhaDigitavel;
    private String          codigoBarras;
    private BigDecimal      valor;
    private LocalDate       vencimento;
}
