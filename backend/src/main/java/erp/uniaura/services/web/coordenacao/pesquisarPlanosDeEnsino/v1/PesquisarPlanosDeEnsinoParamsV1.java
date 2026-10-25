package erp.uniaura.services.web.coordenacao.pesquisarPlanosDeEnsino.v1;

import erp.uniaura.persistence.entities.StatusPlanoEnsino;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarPlanosDeEnsinoParamsV1 {

    @Schema(description = "Status dos planos a listar; sem valor, lista todos")
    private StatusPlanoEnsino status;

    @Schema(description = "Identificador público do curso, para listar só os planos dele")
    private Long cursoUid;

    @Schema(description = "Período letivo, por exemplo 2026.1")
    private String periodoLetivo;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "20")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 20;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (atualizadoEm, submetidoEm, status). Ex.: -atualizadoEm", defaultValue = "-atualizadoEm")
    @Pattern(regexp = "[+-](atualizadoEm|submetidoEm|status)", message = "Ordenação inválida. Use, por exemplo, -atualizadoEm ou +submetidoEm.")
    private String ordem = "-atualizadoEm";
}
