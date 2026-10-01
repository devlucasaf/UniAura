package erp.uniaura.modules.frequencia.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemChamadaRequestDTO {

    @NotNull
    private Long alunoId;

    @NotNull
    private Boolean presente;

    @Size(max = 1000)
    private String justificativa;
}
