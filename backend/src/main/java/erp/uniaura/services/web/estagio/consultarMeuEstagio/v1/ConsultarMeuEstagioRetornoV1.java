package erp.uniaura.services.web.estagio.consultarMeuEstagio.v1;

import erp.uniaura.persistence.entities.StatusEstagio;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ConsultarMeuEstagioRetornoV1 {

    @Schema(description = "Identificador público do estágio")
    private Long            uid;
    private String          empresaConcedente;
    private String          supervisorEmpresa;
    private String          professorOrientadorNome;
    private LocalDate       dataInicio;
    private LocalDate       dataFimPrevista;
    private Integer         cargaHorariaTotal;
    private Integer         cargaHorariaCumprida;
    private StatusEstagio   status;
    private String          termoCompromissoUrl;
}
