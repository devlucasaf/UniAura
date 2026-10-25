package erp.uniaura.services.web.estagio.abrirEstagio.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AbrirEstagioParamsV1 {

    @Schema(description = "Identificador público do aluno")
    @NotNull(message = "O aluno é obrigatório.")
    private Long alunoUid;

    @Schema(description = "Empresa que concede o estágio")
    @NotBlank(message = "A empresa concedente é obrigatória.")
    @Size(max = 200, message = "A empresa concedente deve ter no máximo 200 caracteres.")
    private String empresaConcedente;

    @Schema(description = "Supervisor do estágio na empresa")
    @Size(max = 150, message = "O supervisor deve ter no máximo 150 caracteres.")
    private String supervisorEmpresa;

    @Schema(description = "Identificador público do professor orientador")
    private Long professorOrientadorUid;

    @Schema(description = "Data de início do estágio")
    @NotNull(message = "A data de início é obrigatória.")
    private LocalDate dataInicio;

    @Schema(description = "Data prevista para o fim do estágio")
    @NotNull(message = "A data de fim prevista é obrigatória.")
    private LocalDate dataFimPrevista;

    @Schema(description = "Carga horária total do estágio, em horas")
    @NotNull(message = "A carga horária total é obrigatória.")
    @Positive(message = "A carga horária total deve ser maior que zero.")
    private Integer cargaHorariaTotal;
}
