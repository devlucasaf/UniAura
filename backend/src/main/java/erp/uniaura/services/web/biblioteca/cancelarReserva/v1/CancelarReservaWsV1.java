package erp.uniaura.services.web.biblioteca.cancelarReserva.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class CancelarReservaWsV1 extends BaseWs {

    private final CancelarReservaServiceV1 cancelarReservaServiceV1;

    // --- ENDPOINT QUE CANCELA UMA RESERVA E REORDENA A FILA ---
    @PostMapping
    @IsAuthenticated
    @Operation(summary = "Cancela uma reserva",
            description = "Marca a reserva como CANCELADA e refaz as posições da fila. A equipe da biblioteca cancela qualquer reserva; os demais usuários só as próprias. "
                    + "Erro 422 se a reserva não existir ou for de outro usuário.")
    public void cancelar(@LoggedInUserId String usuarioId, @Valid @RequestBody CancelarReservaParamsV1 params) {
        cancelarReservaServiceV1.cancelar(Long.valueOf(usuarioId), params);
    }
}
