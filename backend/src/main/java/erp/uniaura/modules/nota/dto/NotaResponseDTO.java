package erp.uniaura.modules.nota.dto;

import erp.uniaura.modules.nota.model.PeriodoAvaliacao;
import erp.uniaura.modules.nota.model.TipoAvaliacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotaResponseDTO {

    private Long                id;
    private Long                alunoId;
    private String              alunoNome;
    private String              alunoMatriculaRA;
    private Long                turmaDisciplinaId;
    private Long                turmaId;
    private String              turmaCodigo;
    private Long                disciplinaId;
    private String              disciplinaNome;
    private Long                professorId;
    private String              professorNome;
    private PeriodoAvaliacao    periodoAvaliacao;
    private TipoAvaliacao       tipoAvaliacao;
    private BigDecimal          valor;
    private BigDecimal          peso;
    private String              observacoes;
    private Long                lancadaPorId;
    private String              lancadaPorNome;
    private LocalDateTime       lancadaEm;
    private LocalDateTime       atualizadaEm;
}

