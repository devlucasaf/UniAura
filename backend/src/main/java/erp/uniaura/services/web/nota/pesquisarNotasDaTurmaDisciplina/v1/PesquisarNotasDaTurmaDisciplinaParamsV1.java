package erp.uniaura.services.web.nota.pesquisarNotasDaTurmaDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarNotasDaTurmaDisciplinaParamsV1 {

    @Schema(description = "Identificador público da turma")
    @NotNull(message = "O uid da turma é obrigatório.")
    private Long turmaUid;

    @Schema(description = "Identificador público da disciplina")
    @NotNull(message = "O uid da disciplina é obrigatório.")
    private Long disciplinaUid;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "50")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 50;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (lancadaEm, valor, periodoAvaliacao, tipoAvaliacao). Ex.: -lancadaEm", defaultValue = "-lancadaEm")
    @Pattern(regexp = "[+-](lancadaEm|valor|periodoAvaliacao|tipoAvaliacao)", message = "Ordenação inválida. Use, por exemplo, -lancadaEm ou +valor.")
    private String ordem = "-lancadaEm";
}
