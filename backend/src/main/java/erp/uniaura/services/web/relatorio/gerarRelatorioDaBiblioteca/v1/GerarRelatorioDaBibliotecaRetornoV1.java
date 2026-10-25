package erp.uniaura.services.web.relatorio.gerarRelatorioDaBiblioteca.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GerarRelatorioDaBibliotecaRetornoV1 {

    private Long        totalExemplares;
    private Long        exemplaresDisponiveis;
    private Long        exemplaresEmprestados;
    private Long        exemplaresReservados;

    @Schema(description = "Exemplares emprestados sobre o total de exemplares, em percentual")
    private BigDecimal  taxaUtilizacaoPercentual;
    private Long        multasPendentes;
    private BigDecimal  valorMultasPendentes;
}
