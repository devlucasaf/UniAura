package erp.uniaura.services.web.tcc.agendarDefesaDoTcc.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AgendarDefesaDoTccParamsV1 {

    @Schema(description = "Identificador público do TCC")
    @NotNull(message = "O uid do TCC é obrigatório.")
    private Long uid;

    @Schema(description = "Data da defesa")
    @NotNull(message = "A data da defesa é obrigatória.")
    private LocalDate dataDefesa;
}
