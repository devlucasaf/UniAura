package erp.uniaura.services.web.calendario.pesquisarEventosPublicos.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarEventosPublicosRetornoV1 {

    @Schema(description = "Eventos públicos, do mais próximo ao mais distante")
    private List<PesquisarEventosPublicosEventoRetornoV1> lista;
}
