package erp.uniaura.services.web.nota.alterarNota.v1;

import erp.uniaura.persistence.entities.PeriodoAvaliacao;
import erp.uniaura.persistence.entities.TipoAvaliacao;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AlterarNotaParamsV1 {

    @Schema(description = "Identificador público da nota a alterar")
    @NotNull(message = "O uid da nota é obrigatório.")
    private Long uid;

    @Schema(description = "Período da avaliação")
    @NotNull(message = "O período da avaliação é obrigatório.")
    private PeriodoAvaliacao periodoAvaliacao;

    @Schema(description = "Tipo da avaliação")
    @NotNull(message = "O tipo da avaliação é obrigatório.")
    private TipoAvaliacao tipoAvaliacao;

    @Schema(description = "Nota, de 0 a 10")
    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.00", message = "A nota mínima é 0.")
    @DecimalMax(value = "10.00", message = "A nota máxima é 10.")
    private BigDecimal valor;

    @Schema(description = "Peso na média ponderada; sem valor, mantém o atual")
    @Positive(message = "O peso deve ser maior que zero.")
    private BigDecimal peso;

    @Schema(description = "Observações do professor")
    @Size(max = 1000, message = "As observações devem ter no máximo 1000 caracteres.")
    private String observacoes;
}
