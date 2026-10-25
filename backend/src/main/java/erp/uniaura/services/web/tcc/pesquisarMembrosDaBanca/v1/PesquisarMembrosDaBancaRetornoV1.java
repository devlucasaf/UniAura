package erp.uniaura.services.web.tcc.pesquisarMembrosDaBanca.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarMembrosDaBancaRetornoV1 {

    @Schema(description = "Total de membros da banca, ignorando a paginação")
    private Long total;

    @Schema(description = "Membros da página pedida")
    private List<PesquisarMembrosDaBancaMembroRetornoV1> lista;
}
