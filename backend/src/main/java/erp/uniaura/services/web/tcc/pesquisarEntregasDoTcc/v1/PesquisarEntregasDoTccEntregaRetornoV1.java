package erp.uniaura.services.web.tcc.pesquisarEntregasDoTcc.v1;

import erp.uniaura.persistence.entities.StatusEntregaTcc;
import erp.uniaura.persistence.entities.TipoEntregaTcc;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarEntregasDoTccEntregaRetornoV1 {

    @Schema(description = "Identificador público da entrega")
    private Long             uid;

    private TipoEntregaTcc   tipo;
    private String           arquivoUrl;
    private StatusEntregaTcc status;
    private String           observacoesOrientador;
    private LocalDateTime    analisadoEm;
    private LocalDateTime    enviadoEm;
}
