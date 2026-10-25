package erp.uniaura.services.web.matricula.pesquisarMatriculas.v1;

import erp.uniaura.persistence.entities.StatusMatricula;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarMatriculasParamsV1 {

    @Schema(description = "Filtra pelo aluno (uid); com ele, a lista é o histórico de matrículas do aluno")
    private Long alunoUid;

    @Schema(description = "Filtra pelo status; sem valor, traz todos")
    private StatusMatricula status;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "20")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 20;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (dataMatricula, status, criadoEm). Ex.: -dataMatricula", defaultValue = "-dataMatricula")
    @Pattern(regexp = "[+-](dataMatricula|status|criadoEm)", message = "Ordenação inválida. Use, por exemplo, -dataMatricula ou +status.")
    private String ordem = "-dataMatricula";
}
