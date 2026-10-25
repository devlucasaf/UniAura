package erp.uniaura.services.web.atividade.pesquisarAtividadesDaTurmaDisciplina.v1;

import erp.uniaura.persistence.entities.TipoAtividade;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarAtividadesDaTurmaDisciplinaAtividadeRetornoV1 {

    @Schema(description = "Identificador público da atividade")
    private Long            uid;
    private String          titulo;
    private TipoAtividade   tipo;
    private LocalDateTime   dataPostagem;
    private LocalDateTime   dataEntrega;
    private BigDecimal      valorMaximo;
    private String          professorNome;
    private Boolean         ativa;
}
