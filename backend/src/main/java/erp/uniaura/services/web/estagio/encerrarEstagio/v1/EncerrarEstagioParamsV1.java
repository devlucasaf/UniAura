package erp.uniaura.services.web.estagio.encerrarEstagio.v1;

import erp.uniaura.persistence.entities.StatusEstagio;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EncerrarEstagioParamsV1 {

    @Schema(description = "Identificador público do estágio")
    @NotNull(message = "O uid do estágio é obrigatório.")
    private Long uid;

    @Schema(description = "Status de encerramento: CONCLUIDO, TRANCADO ou CANCELADO")
    @NotNull(message = "O status de encerramento é obrigatório.")
    private StatusEstagio status;

    @Schema(description = "Data em que o estágio terminou de fato")
    @NotNull(message = "A data de fim efetiva é obrigatória.")
    private LocalDate dataFimEfetiva;
}
