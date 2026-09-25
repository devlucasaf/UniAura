package erp.uniaura.modules.comunicado.dto;

import erp.uniaura.modules.comunicado.model.PublicoAlvoComunicado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComunicadoRequestDTO {

    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 150)
    private String titulo;

    @NotBlank(message = "A mensagem é obrigatória.")
    @Size(max = 2000)
    private String mensagem;

    @NotNull(message = "O público-alvo é obrigatório.")
    private PublicoAlvoComunicado publicoAlvo;

    private UUID turmaId;

    private Boolean importante;
}
