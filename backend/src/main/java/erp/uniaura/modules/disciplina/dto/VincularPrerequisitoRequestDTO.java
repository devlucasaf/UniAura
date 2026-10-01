package erp.uniaura.modules.disciplina.dto;

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
public class VincularPrerequisitoRequestDTO {

    @NotNull(message = "O id da disciplina pré-requisito é obrigatório.")
    private Long prerequisitoId;
}

