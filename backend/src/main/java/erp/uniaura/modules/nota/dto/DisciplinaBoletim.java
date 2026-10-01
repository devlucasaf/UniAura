package erp.uniaura.modules.nota.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisciplinaBoletim {

    private Long                    disciplinaId;
    private String                  disciplinaCodigo;
    private String                  disciplinaNome;
    private BigDecimal              mediaFinal;
    private List<NotaResponseDTO>   notas;
}

