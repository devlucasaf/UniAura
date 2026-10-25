package erp.uniaura.services.web.acompanhamento.pesquisarAcompanhamentosEspeciais.v1;

import erp.uniaura.persistence.entities.StatusAcompanhamento;
import erp.uniaura.persistence.entities.TipoNecessidadeEspecial;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarAcompanhamentosEspeciaisParamsV1 {

    @Schema(description = "Filtra pelo status; sem valor, traz todos")
    private StatusAcompanhamento status;

    @Schema(description = "Filtra pelo tipo de necessidade; sem valor, traz todos")
    private TipoNecessidadeEspecial tipoNecessidade;

    @Schema(description = "Filtra pelo aluno (uid); sem valor, traz de todos os alunos")
    private Long alunoUid;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "20")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 20;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (dataInicio, status, tipoNecessidade). Ex.: -dataInicio", defaultValue = "-dataInicio")
    @Pattern(regexp = "[+-](dataInicio|status|tipoNecessidade)", message = "Ordenação inválida. Use, por exemplo, -dataInicio ou +status.")
    private String ordem = "-dataInicio";
}
