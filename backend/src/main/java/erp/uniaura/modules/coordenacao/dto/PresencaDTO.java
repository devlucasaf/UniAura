package erp.uniaura.modules.coordenacao.dto;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresencaDTO {

    @NotNull
    private Long    usuarioId;

    @NotNull
    private Boolean presente;
}
