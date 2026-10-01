package erp.uniaura.modules.tcc.dto;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendarDefesaRequestDTO {

    @NotNull(message = "A data da defesa é obrigatória.")
    private LocalDate dataDefesa;
}
