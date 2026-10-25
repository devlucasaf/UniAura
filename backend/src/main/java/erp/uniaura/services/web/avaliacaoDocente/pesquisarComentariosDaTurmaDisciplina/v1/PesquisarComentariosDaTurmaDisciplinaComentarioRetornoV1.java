package erp.uniaura.services.web.avaliacaoDocente.pesquisarComentariosDaTurmaDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarComentariosDaTurmaDisciplinaComentarioRetornoV1 {

    @Schema(description = "Média simples das três notas da avaliação; o autor não é identificado")
    private BigDecimal      notaGeral;
    private String          comentario;
    private LocalDateTime   criadoEm;
}
