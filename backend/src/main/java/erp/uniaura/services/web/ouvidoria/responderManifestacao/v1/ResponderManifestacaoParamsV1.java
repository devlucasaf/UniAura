package erp.uniaura.services.web.ouvidoria.responderManifestacao.v1;

import erp.uniaura.persistence.entities.StatusManifestacao;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResponderManifestacaoParamsV1 {

    @Schema(description = "Identificador público da manifestação")
    @NotNull(message = "O uid da manifestação é obrigatório.")
    private Long uid;

    @Schema(description = "Novo status da manifestação")
    @NotNull(message = "O novo status é obrigatório.")
    private StatusManifestacao status;

    @Schema(description = "Mensagem da resposta")
    @NotNull(message = "A mensagem é obrigatória.")
    @Size(min = 5, max = 4000, message = "A mensagem deve ter entre 5 e 4000 caracteres.")
    private String mensagem;

    @Schema(description = "Novo prazo para a resposta final")
    private LocalDate prazoResposta;

    @Schema(description = "Falso registra uma nota interna, que o autor não enxerga", defaultValue = "true")
    private Boolean visivelParaAutor = true;
}
