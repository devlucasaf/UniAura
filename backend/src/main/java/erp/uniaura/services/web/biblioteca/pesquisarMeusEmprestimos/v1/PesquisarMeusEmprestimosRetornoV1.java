package erp.uniaura.services.web.biblioteca.pesquisarMeusEmprestimos.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarMeusEmprestimosRetornoV1 {

    @Schema(description = "Total de empréstimos do usuário, ignorando a paginação")
    private Long total;

    @Schema(description = "Empréstimos da página pedida")
    private List<PesquisarMeusEmprestimosEmprestimoRetornoV1> lista;
}
