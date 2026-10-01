package erp.uniaura.modules.coordenacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipanteReuniaoResponseDTO {

    private Long    id;
    private Long    usuarioId;
    private String  usuarioNome;
    private String  papel;
    private Boolean presente;
}
