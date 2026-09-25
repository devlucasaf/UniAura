package erp.uniaura.modules.financeiro.dto;

import erp.uniaura.modules.financeiro.model.FormaPagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoResponseDTO {

    private UUID            id;
    private FormaPagamento  formaPagamento;
    private BigDecimal      valorPago;
    private String          cartaoFinal;
    private LocalDateTime   dataPagamento;
}
