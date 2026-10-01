package erp.uniaura.modules.tcc.dto;

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
public class BancaTccMembroResponseDTO {

    private Long         id;
    private Long         tccId;
    private Long         professorId;
    private String       professorNome;
    private BigDecimal   nota;
    private String       parecer;
}
