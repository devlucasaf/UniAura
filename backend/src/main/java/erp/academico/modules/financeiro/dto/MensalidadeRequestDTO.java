package erp.academico.modules.financeiro.dto;

import erp.academico.modules.financeiro.model.StatusMensalidade;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensalidadeRequestDTO {

    @NotNull(message = "O aluno é obrigatório.")
    private UUID alunoId;

    @NotNull(message = "A competência é obrigatória.")
    @Pattern(regexp = "\\d{4}-\\d{2}", message = "Informe a competência no formato AAAA-MM.")
    private String competencia;

    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    @NotNull(message = "O vencimento é obrigatório.")
    private LocalDate vencimento;

    private StatusMensalidade status;

    @Size(max = 255)
    private String descricao;
}
