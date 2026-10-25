package erp.uniaura.services.web.biblioteca.consultarConfiguracaoDaBiblioteca.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConsultarConfiguracaoDaBibliotecaRetornoV1 {

    @Schema(description = "Prazo do empréstimo para alunos, em dias")
    private Integer prazoEmprestimoAluno;

    @Schema(description = "Prazo do empréstimo para professores, em dias")
    private Integer prazoEmprestimoProfessor;

    @Schema(description = "Máximo de empréstimos ativos ao mesmo tempo por usuário")
    private Integer maximosEmprestimosSimultaneos;

    @Schema(description = "Máximo de renovações por empréstimo")
    private Integer maximasRenovacoes;

    @Schema(description = "Valor da multa por dia de atraso")
    private BigDecimal valorMultaDia;
}
