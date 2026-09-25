package erp.uniaura.modules.financeiro.dto;

import erp.uniaura.modules.financeiro.model.StatusMensalidade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensalidadeResponseDTO {

    private UUID                    id;
    private UUID                    alunoId;
    private String                  alunoNome;
    private String                  competencia;
    private BigDecimal              valor;
    private LocalDate               vencimento;
    private StatusMensalidade       status;
    private String                  descricao;
    private PagamentoResponseDTO    pagamento;
    private LocalDateTime           criadoEm;
    private LocalDateTime           atualizadoEm;
}
