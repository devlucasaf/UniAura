package erp.uniaura.services.web.turma.pesquisarTurmas.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarTurmasParamsV1 {

    @Schema(description = "Filtra pelo código exato da turma")
    private String codigo;

    @Schema(description = "Filtra pelas turmas do curso com este uid")
    private Long cursoUid;

    @Schema(description = "Filtra pelo período letivo (ex.: 2026.1)")
    private String periodoLetivo;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "20")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 20;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (codigo, periodoLetivo, serie, capacidadeMaxima). Ex.: +codigo", defaultValue = "+codigo")
    @Pattern(regexp = "[+-](codigo|periodoLetivo|serie|capacidadeMaxima)", message = "Ordenação inválida. Use, por exemplo, +codigo ou -periodoLetivo.")
    private String ordem = "+codigo";
}
