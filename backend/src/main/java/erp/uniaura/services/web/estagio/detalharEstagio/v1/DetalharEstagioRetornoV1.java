package erp.uniaura.services.web.estagio.detalharEstagio.v1;

import erp.uniaura.persistence.entities.StatusEstagio;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharEstagioRetornoV1 {

    @Schema(description = "Identificador público do estágio")
    private Long          uid;

    @Schema(description = "Identificador público do aluno")
    private Long          alunoUid;

    private String        alunoNome;
    private String        empresaConcedente;
    private String        supervisorEmpresa;
    private String        professorOrientadorNome;
    private LocalDate     dataInicio;
    private LocalDate     dataFimPrevista;
    private LocalDate     dataFimEfetiva;
    private Integer       cargaHorariaTotal;
    private Integer       cargaHorariaCumprida;
    private StatusEstagio status;
    private String        termoCompromissoUrl;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
