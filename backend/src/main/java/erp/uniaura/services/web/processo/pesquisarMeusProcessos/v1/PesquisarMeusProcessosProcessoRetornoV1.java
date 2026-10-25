package erp.uniaura.services.web.processo.pesquisarMeusProcessos.v1;

import erp.uniaura.persistence.entities.StatusProcesso;
import erp.uniaura.persistence.entities.TipoProcesso;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarMeusProcessosProcessoRetornoV1 {

    @Schema(description = "Identificador público do processo")
    private Long            uid;
    private String          protocolo;
    private TipoProcesso    tipo;
    private String          assunto;
    private StatusProcesso  status;
    private LocalDate       prazoResposta;
    private LocalDateTime   criadoEm;
}
