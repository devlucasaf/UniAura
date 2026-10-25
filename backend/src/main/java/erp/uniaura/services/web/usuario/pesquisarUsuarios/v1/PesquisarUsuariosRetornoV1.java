package erp.uniaura.services.web.usuario.pesquisarUsuarios.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarUsuariosRetornoV1 {

    @Schema(description = "Total de usuários que atendem ao filtro, sem considerar a paginação")
    private Long                                  total;

    @Schema(description = "Usuários da página pedida")
    private List<PesquisarUsuariosUsuarioRetornoV1> lista;
}
