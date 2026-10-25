package erp.uniaura.services.web.coordenacao.detalharReuniaoDoColegiado.v1;

import erp.uniaura.persistence.entities.StatusReuniao;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DetalharReuniaoDoColegiadoRetornoV1 {

    @Schema(description = "Identificador público da reunião")
    private Long                                                    uid;
    private Long                                                    cursoUid;
    private String                                                  cursoNome;
    private String                                                  titulo;
    private LocalDateTime                                           dataHora;
    private String                                                  local;
    private String                                                  pauta;
    private StatusReuniao                                           status;
    private String                                                  deliberacoes;
    private String                                                  motivoCancelamento;
    private LocalDateTime                                           encerradaEm;
    private String                                                  criadaPorNome;
    private LocalDateTime                                           criadoEm;
    private LocalDateTime                                           atualizadoEm;
    private List<DetalharReuniaoDoColegiadoParticipanteRetornoV1>   participantes;
}
