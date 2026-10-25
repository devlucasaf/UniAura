package erp.uniaura.services.web.comunicado.pesquisarMural.v1;

import erp.uniaura.persistence.entities.PublicoAlvoComunicado;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarMuralComunicadoRetornoV1 {

    @Schema(description = "Identificador público do comunicado")
    private Long                  uid;

    private String                titulo;
    private String                mensagem;
    private PublicoAlvoComunicado publicoAlvo;

    @Schema(description = "Identificador público da turma, quando o público-alvo é TURMA")
    private Long                  turmaUid;

    private Boolean               importante;
    private String                autorNome;
    private LocalDateTime         criadoEm;
}
