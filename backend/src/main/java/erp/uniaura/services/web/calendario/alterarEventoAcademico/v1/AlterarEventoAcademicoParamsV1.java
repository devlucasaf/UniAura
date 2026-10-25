package erp.uniaura.services.web.calendario.alterarEventoAcademico.v1;

import erp.uniaura.persistence.entities.TipoEvento;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AlterarEventoAcademicoParamsV1 {

    @Schema(description = "Identificador público do evento a alterar")
    @NotNull(message = "O uid do evento é obrigatório.")
    private Long uid;

    @Schema(description = "Título do evento")
    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 150, message = "O título deve ter no máximo 150 caracteres.")
    private String titulo;

    @Schema(description = "Descrição do evento")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
    private String descricao;

    @Schema(description = "Data de início")
    @NotNull(message = "A data de início é obrigatória.")
    private LocalDate dataInicio;

    @Schema(description = "Data de término (opcional)")
    private LocalDate dataFim;

    @Schema(description = "Tipo do evento")
    @NotNull(message = "O tipo é obrigatório.")
    private TipoEvento tipo;

    @Schema(description = "Se o evento aparece no calendário público do site; sem valor, deixa de ser público")
    private Boolean publico;
}
