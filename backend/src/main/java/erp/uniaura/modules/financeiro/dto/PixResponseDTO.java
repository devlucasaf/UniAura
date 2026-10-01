package erp.uniaura.modules.financeiro.dto;

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
public class PixResponseDTO {

    private String          payload;
    private BigDecimal      valor;
    private LocalDateTime   expiraEm;
}
