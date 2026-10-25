package erp.uniaura.services.web.tcc.consultarMeuTcc.v1;

import erp.uniaura.persistence.entities.StatusTcc;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ConsultarMeuTccRetornoV1 {

    @Schema(description = "Identificador público do TCC")
    private Long      uid;

    private String    titulo;
    private String    professorOrientadorNome;
    private String    professorCoorientadorNome;
    private String    periodoLetivo;
    private StatusTcc status;
    private LocalDate dataDefesa;
}
