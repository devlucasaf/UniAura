package erp.uniaura.services.web.estagio.pesquisarEstagios.v1;

import erp.uniaura.persistence.entities.StatusEstagio;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PesquisarEstagiosEstagioRetornoV1 {

    @Schema(description = "Identificador público do estágio")
    private Long          uid;

    @Schema(description = "Identificador público do aluno")
    private Long          alunoUid;
    private String        alunoNome;
    private String        empresaConcedente;
    private String        professorOrientadorNome;
    private LocalDate     dataInicio;
    private LocalDate     dataFimPrevista;
    private Integer       cargaHorariaTotal;
    private Integer       cargaHorariaCumprida;
    private StatusEstagio status;
}
