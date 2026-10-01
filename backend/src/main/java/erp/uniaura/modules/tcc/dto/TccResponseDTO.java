package erp.uniaura.modules.tcc.dto;

import erp.uniaura.modules.tcc.model.StatusTcc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TccResponseDTO {

    private Long            id;
    private Long            alunoId;
    private String          alunoNome;
    private String          titulo;
    private Long            professorOrientadorId;
    private String          professorOrientadorNome;
    private Long            professorCoorientadorId;
    private String          professorCoorientadorNome;
    private String          periodoLetivo;
    private StatusTcc       status;
    private BigDecimal      notaFinal;
    private LocalDate       dataDefesa;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}
