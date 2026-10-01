package erp.uniaura.modules.biblioteca.emprestimo.dto;

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
public class EmprestimoRequestDTO {

    private Long    exemplarId;
    private String  codigoBarras;

    @NotNull
    private Long usuarioId;
}

