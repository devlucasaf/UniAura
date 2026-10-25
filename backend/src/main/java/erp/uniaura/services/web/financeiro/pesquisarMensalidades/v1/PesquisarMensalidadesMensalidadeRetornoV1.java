package erp.uniaura.services.web.financeiro.pesquisarMensalidades.v1;

import erp.uniaura.persistence.entities.FormaPagamento;
import erp.uniaura.persistence.entities.StatusMensalidade;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarMensalidadesMensalidadeRetornoV1 {

    @Schema(description = "Identificador público da mensalidade")
    private Long                uid;

    @Schema(description = "Identificador público do aluno")
    private Long                alunoUid;
    private String              alunoNome;
    private String              competencia;
    private BigDecimal          valor;
    private LocalDate           vencimento;
    private StatusMensalidade   status;
    private String              descricao;

    @Schema(description = "Forma de pagamento; vazio enquanto a mensalidade não foi paga")
    private FormaPagamento      formaPagamento;
    private BigDecimal          valorPago;
    private BigDecimal          valorMulta;
    private LocalDateTime       dataPagamento;
}
