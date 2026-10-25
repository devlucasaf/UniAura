package erp.uniaura.services.web.comunicado.pesquisarMural.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarMuralRetornoV1 {

    @Schema(description = "Até 30 comunicados relevantes para quem está logado, dos mais recentes aos mais antigos")
    private List<PesquisarMuralComunicadoRetornoV1> lista;
}
