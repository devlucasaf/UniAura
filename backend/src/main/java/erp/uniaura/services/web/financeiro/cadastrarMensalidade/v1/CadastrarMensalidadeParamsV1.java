package erp.uniaura.services.web.financeiro.cadastrarMensalidade.v1;

import erp.uniaura.persistence.entities.StatusMensalidade;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CadastrarMensalidadeParamsV1 {

    @Schema(description = "Identificador público do aluno cobrado")
    @NotNull(message = "O aluno é obrigatório.")
    private Long alunoUid;

    @Schema(description = "Competência da mensalidade, no formato AAAA-MM", example = "2026-03")
    @NotNull(message = "A competência é obrigatória.")
    @Pattern(regexp = "\\d{4}-\\d{2}", message = "Informe a competência no formato AAAA-MM.")
    private String competencia;

    @Schema(description = "Valor da mensalidade, maior que zero")
    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    @Schema(description = "Data de vencimento")
    @NotNull(message = "O vencimento é obrigatório.")
    private LocalDate vencimento;

    @Schema(description = "Status inicial; sem valor, a mensalidade nasce PENDENTE", defaultValue = "PENDENTE")
    private StatusMensalidade status;

    @Schema(description = "Descrição livre, de até 255 caracteres")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String descricao;
}
