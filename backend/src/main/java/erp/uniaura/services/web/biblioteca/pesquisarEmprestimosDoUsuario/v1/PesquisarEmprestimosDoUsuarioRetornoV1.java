package erp.uniaura.services.web.biblioteca.pesquisarEmprestimosDoUsuario.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarEmprestimosDoUsuarioRetornoV1 {

    @Schema(description = "Total de empréstimos do usuário, ignorando a paginação")
    private Long total;

    @Schema(description = "Empréstimos da página pedida")
    private List<PesquisarEmprestimosDoUsuarioEmprestimoRetornoV1> lista;
}
