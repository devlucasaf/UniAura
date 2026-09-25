package erp.uniaura.modules.coordenacao.dto;

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
public class ParticipanteReuniaoResponseDTO {

    private UUID    id;
    private UUID    usuarioId;
    private String  usuarioNome;
    private String  papel;
    private Boolean presente;
}
