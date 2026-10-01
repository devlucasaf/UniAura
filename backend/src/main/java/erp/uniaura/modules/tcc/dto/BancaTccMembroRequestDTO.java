package erp.uniaura.modules.tcc.dto;

import jakarta.validation.constraints.NotNull;

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
public class BancaTccMembroRequestDTO {

    @NotNull(message = "O professor é obrigatório.")
    private Long professorId;
}
