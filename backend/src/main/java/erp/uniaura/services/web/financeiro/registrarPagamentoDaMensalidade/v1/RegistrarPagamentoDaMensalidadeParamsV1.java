package erp.uniaura.services.web.financeiro.registrarPagamentoDaMensalidade.v1;

import erp.uniaura.persistence.entities.FormaPagamento;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarPagamentoDaMensalidadeParamsV1 {

    @Schema(description = "Identificador público da mensalidade a pagar")
    @NotNull(message = "O uid da mensalidade é obrigatório.")
    private Long uid;

    @Schema(description = "Forma de pagamento escolhida")
    @NotNull(message = "A forma de pagamento é obrigatória.")
    private FormaPagamento formaPagamento;

    @Schema(description = "Número do cartão fictício, de 13 a 19 dígitos; obrigatório no pagamento com cartão")
    @Pattern(regexp = "\\d{13,19}", message = "Informe um número de cartão fictício com 13 a 19 dígitos.")
    private String cartaoNumero;

    @Schema(description = "Validade do cartão, no formato MM/AA; obrigatória no pagamento com cartão", example = "12/30")
    @Pattern(regexp = "\\d{2}/\\d{2,4}", message = "Informe a validade no formato MM/AA.")
    private String cartaoValidade;

    @Schema(description = "CVV do cartão, de 3 ou 4 dígitos; obrigatório no pagamento com cartão")
    @Pattern(regexp = "\\d{3,4}", message = "Informe um CVV válido.")
    private String cartaoCvv;
}
