package erp.uniaura.services.web.calendario.pesquisarEventosAcademicos.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarEventosAcademicosRetornoV1 {

    @Schema(description = "Total de eventos que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Eventos da página pedida")
    private List<PesquisarEventosAcademicosEventoRetornoV1> lista;
}
