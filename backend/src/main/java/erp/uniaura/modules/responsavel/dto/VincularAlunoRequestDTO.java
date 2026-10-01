package erp.uniaura.modules.responsavel.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
public class VincularAlunoRequestDTO {

    @NotNull(message = "O id do aluno é obrigatório.")
    private Long alunoId;

    @Size(max = 500)
    private String observacao;
}

