package erp.uniaura.services.web.coordenacao.removerParticipanteDaReuniao.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverParticipanteDaReuniaoParamsV1 {

    @Schema(description = "Identificador público da reunião")
    @NotNull(message = "O uid da reunião é obrigatório.")
    private Long reuniaoUid;

    @Schema(description = "Identificador público do usuário convocado a remover")
    @NotNull(message = "O uid do usuário é obrigatório.")
    private Long usuarioUid;
}
