package erp.uniaura.services.web.biblioteca.cancelarReserva.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelarReservaParamsV1 {

    @Schema(description = "Identificador público da reserva a cancelar")
    @NotNull(message = "O uid da reserva é obrigatório.")
    private Long uid;
}
