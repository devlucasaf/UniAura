package erp.uniaura.services.web.processo.detalharProcesso.v1;

import erp.uniaura.persistence.entities.StatusProcesso;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharProcessoMovimentacaoRetornoV1 {

    @Schema(description = "Identificador público da movimentação")
    private Long           uid;

    private String         autorNome;
    private StatusProcesso statusAnterior;
    private StatusProcesso statusNovo;
    private String         comentario;

    @Schema(description = "Falso indica despacho interno, que o aluno não enxerga")
    private Boolean        visivelParaAluno;

    private LocalDateTime  criadoEm;
}
