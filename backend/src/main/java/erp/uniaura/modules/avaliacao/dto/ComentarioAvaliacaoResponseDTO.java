package erp.uniaura.modules.avaliacao.dto;

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
public class ComentarioAvaliacaoResponseDTO {

    private BigDecimal     notaGeral;
    private String         comentario;
    private LocalDateTime  criadoEm;
}
