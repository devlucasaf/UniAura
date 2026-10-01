package erp.uniaura.modules.estagio.dto;

import erp.uniaura.modules.estagio.model.StatusEstagio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstagioResponseDTO {

    private Long            id;
    private Long            alunoId;
    private String          alunoNome;
    private String          empresaConcedente;
    private String          supervisorEmpresa;
    private Long            professorOrientadorId;
    private String          professorOrientadorNome;
    private LocalDate       dataInicio;
    private LocalDate       dataFimPrevista;
    private LocalDate       dataFimEfetiva;
    private Integer         cargaHorariaTotal;
    private Integer         cargaHorariaCumprida;
    private StatusEstagio   status;
    private String          termoCompromissoUrl;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}
