package erp.uniaura.services.web.atividade.detalharAtividade.v1;

import erp.uniaura.persistence.entities.TipoAtividade;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharAtividadeRetornoV1 {

    @Schema(description = "Identificador público da atividade")
    private Long            uid;

    @Schema(description = "Identificador público do vínculo entre turma e disciplina")
    private Long            turmaDisciplinaUid;
    private String          titulo;
    private String          descricao;
    private TipoAtividade   tipo;
    private LocalDateTime   dataPostagem;
    private LocalDateTime   dataEntrega;
    private BigDecimal      valorMaximo;
    private String          professorNome;
    private Boolean         ativa;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}
