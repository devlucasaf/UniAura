package erp.uniaura.modules.coordenacao.dto;

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
public class ConvocarParticipanteRequestDTO {

    @NotNull
    private UUID    usuarioId;

    @Size(max = 100)
    private String  papel;
}
