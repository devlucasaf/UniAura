package erp.uniaura.services.web.atividade.pesquisarEntregasDaAtividade.v1;

import erp.uniaura.persistence.entities.StatusEntrega;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarEntregasDaAtividadeParamsV1 {

    @Schema(description = "Identificador público da atividade")
    @NotNull(message = "O uid da atividade é obrigatório.")
    private Long atividadeUid;

    @Schema(description = "Filtra pelo status da entrega; sem valor, traz todas")
    private StatusEntrega status;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "20")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 20;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (dataEntrega, status, nota). Ex.: +dataEntrega", defaultValue = "+dataEntrega")
    @Pattern(regexp = "[+-](dataEntrega|status|nota)", message = "Ordenação inválida. Use, por exemplo, +dataEntrega ou -nota.")
    private String ordem = "+dataEntrega";
}
