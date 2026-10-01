package erp.uniaura.modules.financeiro.dto;

import erp.uniaura.modules.financeiro.model.FormaPagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoResponseDTO {

    private Long            id;
    private FormaPagamento  formaPagamento;
    private BigDecimal      valorPago;
    private BigDecimal      valorMulta;
    private String          cartaoFinal;
    private LocalDateTime   dataPagamento;
}
