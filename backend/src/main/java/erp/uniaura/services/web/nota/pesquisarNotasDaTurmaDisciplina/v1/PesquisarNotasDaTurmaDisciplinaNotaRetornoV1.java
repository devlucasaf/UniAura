package erp.uniaura.services.web.nota.pesquisarNotasDaTurmaDisciplina.v1;

import erp.uniaura.persistence.entities.PeriodoAvaliacao;
import erp.uniaura.persistence.entities.TipoAvaliacao;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarNotasDaTurmaDisciplinaNotaRetornoV1 {

    @Schema(description = "Identificador público da nota")
    private Long                uid;

    @Schema(description = "Identificador público do aluno")
    private Long                alunoUid;
    private String              alunoNome;
    private String              alunoMatriculaRA;
    private PeriodoAvaliacao    periodoAvaliacao;
    private TipoAvaliacao       tipoAvaliacao;
    private BigDecimal          valor;
    private BigDecimal          peso;
    private String              observacoes;
    private String              lancadaPorNome;
    private LocalDateTime       lancadaEm;
}
