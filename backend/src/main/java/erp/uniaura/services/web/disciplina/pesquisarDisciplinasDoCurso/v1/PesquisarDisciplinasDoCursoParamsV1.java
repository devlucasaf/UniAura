package erp.uniaura.services.web.disciplina.pesquisarDisciplinasDoCurso.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarDisciplinasDoCursoParamsV1 {

    @Schema(description = "Identificador público do curso")
    @NotNull(message = "O uid do curso é obrigatório.")
    private Long cursoUid;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "20")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 20;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (periodo, codigo, nome, cargaHoraria). Ex.: +periodo", defaultValue = "+periodo")
    @Pattern(regexp = "[+-](periodo|codigo|nome|cargaHoraria)", message = "Ordenação inválida. Use, por exemplo, +periodo ou -nome.")
    private String ordem = "+periodo";
}
