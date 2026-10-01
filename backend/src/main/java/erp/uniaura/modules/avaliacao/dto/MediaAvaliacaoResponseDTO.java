package erp.uniaura.modules.avaliacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaAvaliacaoResponseDTO {

    private long        quantidadeAvaliacoes;
    private BigDecimal  mediaDidatica;
    private BigDecimal  mediaPontualidade;
    private BigDecimal  mediaDisponibilidade;
    private BigDecimal  mediaGeral;
}
