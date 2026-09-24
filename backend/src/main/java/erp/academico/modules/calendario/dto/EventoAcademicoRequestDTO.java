package erp.academico.modules.calendario.dto;

import erp.academico.modules.calendario.model.TipoEvento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class EventoAcademicoRequestDTO {

    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 150)
    private String titulo;

    @Size(max = 500)
    private String descricao;

    @NotNull(message = "A data de início é obrigatória.")
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @NotNull(message = "O tipo é obrigatório.")
    private TipoEvento tipo;

    private Boolean publico;
}
