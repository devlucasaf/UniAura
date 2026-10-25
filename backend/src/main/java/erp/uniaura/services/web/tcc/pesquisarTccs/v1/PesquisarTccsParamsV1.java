package erp.uniaura.services.web.tcc.pesquisarTccs.v1;

import erp.uniaura.persistence.entities.StatusTcc;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarTccsParamsV1 {

    @Schema(description = "Filtra pelo aluno (uid)")
    private Long alunoUid;

    @Schema(description = "Filtra pelo professor orientador (uid)")
    private Long professorOrientadorUid;

    @Schema(description = "Filtra pelo status; sem valor, traz todos")
    private StatusTcc status;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "20")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 20;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (criadoEm, periodoLetivo, status, dataDefesa). Ex.: -criadoEm", defaultValue = "-criadoEm")
    @Pattern(regexp = "[+-](criadoEm|periodoLetivo|status|dataDefesa)", message = "Ordenação inválida. Use, por exemplo, -criadoEm ou +dataDefesa.")
    private String ordem = "-criadoEm";
}
