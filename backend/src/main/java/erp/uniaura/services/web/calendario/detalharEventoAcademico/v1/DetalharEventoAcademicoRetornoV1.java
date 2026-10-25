package erp.uniaura.services.web.calendario.detalharEventoAcademico.v1;

import erp.uniaura.persistence.entities.TipoEvento;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharEventoAcademicoRetornoV1 {

    @Schema(description = "Identificador público do evento")
    private Long            uid;
    private String          titulo;
    private String          descricao;
    private LocalDate       dataInicio;
    private LocalDate       dataFim;
    private TipoEvento      tipo;
    private Boolean         publico;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}
