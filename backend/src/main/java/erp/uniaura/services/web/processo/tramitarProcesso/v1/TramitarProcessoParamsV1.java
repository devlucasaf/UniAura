package erp.uniaura.services.web.processo.tramitarProcesso.v1;

import erp.uniaura.persistence.entities.StatusProcesso;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TramitarProcessoParamsV1 {

    @Schema(description = "Identificador público do processo")
    @NotNull(message = "O uid do processo é obrigatório.")
    private Long uid;

    @Schema(description = "Novo status do processo")
    @NotNull(message = "O novo status é obrigatório.")
    private StatusProcesso status;

    @Schema(description = "Despacho ou parecer; obrigatório ao deferir ou indeferir")
    @Size(max = 4000, message = "O comentário deve ter no máximo 4000 caracteres.")
    private String comentario;

    @Schema(description = "Novo prazo para a resposta")
    private LocalDate prazoResposta;

    @Schema(description = "Falso registra um despacho interno, que o aluno não enxerga", defaultValue = "true")
    private Boolean visivelParaAluno = true;
}
