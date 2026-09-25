package erp.uniaura.modules.financeiro.dto;

import erp.uniaura.modules.financeiro.model.FormaPagamento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoRequestDTO {

    @NotNull(message = "A forma de pagamento é obrigatória.")
    private FormaPagamento formaPagamento;

    @Pattern(regexp = "\\d{13,19}", message = "Informe um número de cartão fictício com 13 a 19 dígitos.")
    private String cartaoNumero;

    @Pattern(regexp = "\\d{2}/\\d{2,4}", message = "Informe a validade no formato MM/AA.")
    private String cartaoValidade;

    @Pattern(regexp = "\\d{3,4}", message = "Informe um CVV válido.")
    private String cartaoCvv;
}
