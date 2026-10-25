package erp.uniaura.services.web.ouvidoria.pesquisarManifestacoes.v1;

import erp.uniaura.persistence.entities.StatusManifestacao;
import erp.uniaura.persistence.entities.TipoManifestacao;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarManifestacoesParamsV1 {

    @Schema(description = "Filtra pelo status; sem valor, traz todos")
    private StatusManifestacao status;

    @Schema(description = "Filtra pelo tipo; sem valor, traz todos")
    private TipoManifestacao tipo;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "20")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 20;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (criadoEm, status, tipo, prazoResposta). Ex.: -criadoEm", defaultValue = "-criadoEm")
    @Pattern(regexp = "[+-](criadoEm|status|tipo|prazoResposta)", message = "Ordenação inválida. Use, por exemplo, -criadoEm ou +prazoResposta.")
    private String ordem = "-criadoEm";
}
