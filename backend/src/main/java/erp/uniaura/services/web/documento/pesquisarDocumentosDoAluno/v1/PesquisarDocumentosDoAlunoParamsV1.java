package erp.uniaura.services.web.documento.pesquisarDocumentosDoAluno.v1;

import erp.uniaura.persistence.entities.TipoDocumento;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarDocumentosDoAlunoParamsV1 {

    @Schema(description = "Identificador público do aluno")
    @NotNull(message = "O uid do aluno é obrigatório.")
    private Long alunoUid;

    @Schema(description = "Filtra por um tipo de documento; sem valor, traz todos. Com o tipo informado, a lista é o histórico de versões desse tipo")
    private TipoDocumento tipo;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "20")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 20;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (enviadoEm, tipo, status, versao). Ex.: -versao", defaultValue = "-enviadoEm")
    @Pattern(regexp = "[+-](enviadoEm|tipo|status|versao)", message = "Ordenação inválida. Use, por exemplo, -enviadoEm ou -versao.")
    private String ordem = "-enviadoEm";
}
