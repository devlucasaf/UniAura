package erp.uniaura.services.web.nota.pesquisarNotasDoAluno.v1;

import erp.uniaura.persistence.entities.PeriodoAvaliacao;
import erp.uniaura.persistence.entities.TipoAvaliacao;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarNotasDoAlunoNotaRetornoV1 {

    @Schema(description = "Identificador público da nota")
    private Long                uid;
    private String              turmaCodigo;
    private String              disciplinaNome;
    private String              professorNome;
    private PeriodoAvaliacao    periodoAvaliacao;
    private TipoAvaliacao       tipoAvaliacao;
    private BigDecimal          valor;
    private BigDecimal          peso;
    private String              observacoes;
    private LocalDateTime       lancadaEm;
}
