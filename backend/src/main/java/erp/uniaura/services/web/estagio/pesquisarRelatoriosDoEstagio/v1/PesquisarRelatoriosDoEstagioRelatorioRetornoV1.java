package erp.uniaura.services.web.estagio.pesquisarRelatoriosDoEstagio.v1;

import erp.uniaura.persistence.entities.StatusRelatorioEstagio;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarRelatoriosDoEstagioRelatorioRetornoV1 {

    @Schema(description = "Identificador público do relatório")
    private Long                    uid;
    private String                  periodoReferencia;
    private Integer                 horasRegistradas;
    private String                  descricaoAtividades;
    private String                  comprovanteUrl;
    private StatusRelatorioEstagio  status;
    private String                  analisadoPorNome;
    private String                  observacoes;
    private LocalDateTime           analisadoEm;
    private LocalDateTime           enviadoEm;
}
