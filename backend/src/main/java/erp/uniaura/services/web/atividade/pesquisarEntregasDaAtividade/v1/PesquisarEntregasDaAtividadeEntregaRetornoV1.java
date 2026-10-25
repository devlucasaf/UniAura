package erp.uniaura.services.web.atividade.pesquisarEntregasDaAtividade.v1;

import erp.uniaura.persistence.entities.StatusEntrega;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarEntregasDaAtividadeEntregaRetornoV1 {

    @Schema(description = "Identificador público da entrega")
    private Long            uid;

    @Schema(description = "Identificador público do aluno")
    private Long            alunoUid;
    private String          alunoNome;
    private String          arquivoUrl;
    private String          comentarioAluno;
    private LocalDateTime   dataEntrega;
    private BigDecimal      nota;
    private String          feedback;
    private StatusEntrega   status;
}
