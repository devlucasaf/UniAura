package erp.uniaura.services.web.acompanhamento.detalharAcompanhamentoEspecial.v1;

import erp.uniaura.persistence.entities.StatusAcompanhamento;
import erp.uniaura.persistence.entities.TipoNecessidadeEspecial;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharAcompanhamentoEspecialRetornoV1 {

    @Schema(description = "Identificador público do acompanhamento")
    private Long                    uid;

    @Schema(description = "Identificador público do aluno")
    private Long                    alunoUid;

    private String                  alunoNome;
    private String                  matriculaRA;
    private TipoNecessidadeEspecial tipoNecessidade;
    private String                  descricaoNecessidade;
    private String                  recursosNecessarios;
    private Boolean                 possuiLaudo;
    private StatusAcompanhamento    status;
    private LocalDate               dataInicio;
    private LocalDate               dataEncerramento;
    private String                  responsavelPeloAcompanhamento;
    private LocalDateTime           criadoEm;
    private LocalDateTime           atualizadoEm;
}
