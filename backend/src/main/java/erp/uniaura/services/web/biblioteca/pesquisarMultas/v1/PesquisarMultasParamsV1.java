package erp.uniaura.services.web.biblioteca.pesquisarMultas.v1;

import erp.uniaura.persistence.entities.StatusMulta;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarMultasParamsV1 {

    @Schema(description = "Status das multas a listar", defaultValue = "PENDENTE")
    private StatusMulta status = StatusMulta.PENDENTE;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "50")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 50;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (geradaEm, valor, diasAtraso). Ex.: -geradaEm", defaultValue = "-geradaEm")
    @Pattern(regexp = "[+-](geradaEm|valor|diasAtraso)", message = "Ordenação inválida. Use, por exemplo, -geradaEm ou -valor.")
    private String ordem = "-geradaEm";
}
