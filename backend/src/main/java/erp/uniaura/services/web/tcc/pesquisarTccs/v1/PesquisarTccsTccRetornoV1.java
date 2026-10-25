package erp.uniaura.services.web.tcc.pesquisarTccs.v1;

import erp.uniaura.persistence.entities.StatusTcc;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PesquisarTccsTccRetornoV1 {

    @Schema(description = "Identificador público do TCC")
    private Long       uid;

    @Schema(description = "Identificador público do aluno")
    private Long       alunoUid;

    private String     alunoNome;
    private String     titulo;
    private String     professorOrientadorNome;
    private String     periodoLetivo;
    private StatusTcc  status;
    private BigDecimal notaFinal;
    private LocalDate  dataDefesa;
}
