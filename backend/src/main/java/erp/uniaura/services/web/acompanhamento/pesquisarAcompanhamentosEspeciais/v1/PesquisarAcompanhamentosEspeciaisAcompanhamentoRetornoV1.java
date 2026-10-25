package erp.uniaura.services.web.acompanhamento.pesquisarAcompanhamentosEspeciais.v1;

import erp.uniaura.persistence.entities.StatusAcompanhamento;
import erp.uniaura.persistence.entities.TipoNecessidadeEspecial;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PesquisarAcompanhamentosEspeciaisAcompanhamentoRetornoV1 {

    @Schema(description = "Identificador público do acompanhamento")
    private Long                    uid;

    @Schema(description = "Identificador público do aluno")
    private Long                    alunoUid;

    private String                  alunoNome;
    private String                  matriculaRA;
    private TipoNecessidadeEspecial tipoNecessidade;
    private StatusAcompanhamento    status;
    private Boolean                 possuiLaudo;
    private LocalDate               dataInicio;
}
