package erp.uniaura.services.web.coordenacao.pesquisarReunioesDoColegiado.v1;

import erp.uniaura.persistence.entities.StatusReuniao;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarReunioesDoColegiadoReuniaoRetornoV1 {

    @Schema(description = "Identificador público da reunião")
    private Long            uid;
    private Long            cursoUid;
    private String          cursoNome;
    private String          titulo;
    private LocalDateTime   dataHora;
    private String          local;
    private StatusReuniao   status;
    private LocalDateTime   encerradaEm;
    private String          criadaPorNome;
}
