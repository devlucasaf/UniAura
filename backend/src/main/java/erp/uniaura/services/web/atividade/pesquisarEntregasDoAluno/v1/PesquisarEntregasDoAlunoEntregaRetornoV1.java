package erp.uniaura.services.web.atividade.pesquisarEntregasDoAluno.v1;

import erp.uniaura.persistence.entities.StatusEntrega;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarEntregasDoAlunoEntregaRetornoV1 {

    @Schema(description = "Identificador público da entrega")
    private Long            uid;

    @Schema(description = "Identificador público da atividade")
    private Long            atividadeUid;

    private String          atividadeTitulo;
    private String          arquivoUrl;
    private String          comentarioAluno;
    private LocalDateTime   dataEntrega;
    private BigDecimal      nota;
    private String          feedback;
    private StatusEntrega   status;
}
