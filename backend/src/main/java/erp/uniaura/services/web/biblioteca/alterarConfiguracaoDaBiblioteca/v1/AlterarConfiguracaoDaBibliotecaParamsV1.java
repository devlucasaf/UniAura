package erp.uniaura.services.web.biblioteca.alterarConfiguracaoDaBiblioteca.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AlterarConfiguracaoDaBibliotecaParamsV1 {

    @Schema(description = "Prazo do empréstimo para alunos, em dias")
    @NotNull(message = "O prazo de empréstimo do aluno é obrigatório.")
    @Positive(message = "O prazo de empréstimo do aluno deve ser maior que zero.")
    private Integer prazoEmprestimoAluno;

    @Schema(description = "Prazo do empréstimo para professores, em dias")
    @NotNull(message = "O prazo de empréstimo do professor é obrigatório.")
    @Positive(message = "O prazo de empréstimo do professor deve ser maior que zero.")
    private Integer prazoEmprestimoProfessor;

    @Schema(description = "Máximo de empréstimos ativos ao mesmo tempo por usuário")
    @NotNull(message = "O máximo de empréstimos simultâneos é obrigatório.")
    @Positive(message = "O máximo de empréstimos simultâneos deve ser maior que zero.")
    private Integer maximosEmprestimosSimultaneos;

    @Schema(description = "Máximo de renovações por empréstimo; zero desliga a renovação")
    @NotNull(message = "O máximo de renovações é obrigatório.")
    @Min(value = 0, message = "O máximo de renovações não pode ser negativo.")
    private Integer maximasRenovacoes;

    @Schema(description = "Valor da multa por dia de atraso")
    @NotNull(message = "O valor da multa por dia é obrigatório.")
    @DecimalMin(value = "0.00", message = "O valor da multa por dia não pode ser negativo.")
    private BigDecimal valorMultaDia;
}
