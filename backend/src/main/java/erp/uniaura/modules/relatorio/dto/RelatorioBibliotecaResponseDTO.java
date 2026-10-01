package erp.uniaura.modules.relatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioBibliotecaResponseDTO {

    private long        totalExemplares;
    private long        exemplaresDisponiveis;
    private long        exemplaresEmprestados;
    private long        exemplaresReservados;
    private BigDecimal  taxaUtilizacaoPercentual;
    private long        multasPendentes;
    private BigDecimal  valorMultasPendentes;
}
