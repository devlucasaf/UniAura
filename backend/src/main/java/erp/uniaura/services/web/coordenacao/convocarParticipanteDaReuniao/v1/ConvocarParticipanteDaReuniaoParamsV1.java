package erp.uniaura.services.web.coordenacao.convocarParticipanteDaReuniao.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvocarParticipanteDaReuniaoParamsV1 {

    @Schema(description = "Identificador público da reunião")
    @NotNull(message = "O uid da reunião é obrigatório.")
    private Long reuniaoUid;

    @Schema(description = "Identificador público do usuário convocado")
    @NotNull(message = "O uid do usuário é obrigatório.")
    private Long usuarioUid;

    @Schema(description = "Papel do convocado na reunião, de até 100 caracteres")
    @Size(max = 100, message = "O papel deve ter no máximo 100 caracteres.")
    private String papel;
}
