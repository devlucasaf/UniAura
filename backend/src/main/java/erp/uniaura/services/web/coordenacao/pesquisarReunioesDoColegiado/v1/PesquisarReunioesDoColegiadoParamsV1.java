package erp.uniaura.services.web.coordenacao.pesquisarReunioesDoColegiado.v1;

import erp.uniaura.persistence.entities.StatusReuniao;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarReunioesDoColegiadoParamsV1 {

    @Schema(description = "Identificador público do curso, para listar só as reuniões dele")
    private Long cursoUid;

    @Schema(description = "Status das reuniões a listar; sem valor, lista todas")
    private StatusReuniao status;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "20")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 20;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (dataHora, status, titulo). Ex.: -dataHora", defaultValue = "-dataHora")
    @Pattern(regexp = "[+-](dataHora|status|titulo)", message = "Ordenação inválida. Use, por exemplo, -dataHora ou +titulo.")
    private String ordem = "-dataHora";
}
