package erp.uniaura.services.web.coordenacao.detalharReuniaoDoColegiado.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharReuniaoDoColegiadoParticipanteRetornoV1 {

    @Schema(description = "Identificador público do participante convocado")
    private Long    uid;

    @Schema(description = "Identificador público do usuário convocado")
    private Long    usuarioUid;
    private String  usuarioNome;
    private String  papel;

    @Schema(description = "Presença registrada na ata; vazio antes de a reunião acontecer")
    private Boolean presente;
}
