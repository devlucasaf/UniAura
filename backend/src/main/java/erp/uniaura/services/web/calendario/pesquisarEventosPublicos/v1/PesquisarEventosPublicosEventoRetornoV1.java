package erp.uniaura.services.web.calendario.pesquisarEventosPublicos.v1;

import erp.uniaura.persistence.entities.TipoEvento;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PesquisarEventosPublicosEventoRetornoV1 {

    @Schema(description = "Identificador público do evento")
    private Long        uid;
    private String      titulo;
    private String      descricao;
    private LocalDate   dataInicio;
    private LocalDate   dataFim;
    private TipoEvento  tipo;
}
