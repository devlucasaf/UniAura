package erp.uniaura.modules.calendario.dto;

import erp.uniaura.modules.calendario.model.TipoEvento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoAcademicoResponseDTO {

    private UUID            id;
    private String          titulo;
    private String          descricao;
    private LocalDate       dataInicio;
    private LocalDate       dataFim;
    private TipoEvento      tipo;
    private Boolean         publico;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}
