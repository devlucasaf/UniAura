package erp.uniaura.modules.estagio.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

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
public class EstagioRequestDTO {

    @NotNull(message = "O aluno é obrigatório.")
    private Long alunoId;

    @NotNull(message = "A empresa concedente é obrigatória.")
    @Size(max = 200)
    private String empresaConcedente;

    @Size(max = 150)
    private String supervisorEmpresa;

    private Long professorOrientadorId;

    @NotNull(message = "A data de início é obrigatória.")
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim prevista é obrigatória.")
    private LocalDate dataFimPrevista;

    @NotNull(message = "A carga horária total é obrigatória.")
    @Positive
    private Integer cargaHorariaTotal;
}
