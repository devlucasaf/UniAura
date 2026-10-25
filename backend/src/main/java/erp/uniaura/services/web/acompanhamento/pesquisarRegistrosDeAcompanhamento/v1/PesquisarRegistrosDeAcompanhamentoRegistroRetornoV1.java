package erp.uniaura.services.web.acompanhamento.pesquisarRegistrosDeAcompanhamento.v1;

import erp.uniaura.persistence.entities.TipoRegistroAcompanhamento;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PesquisarRegistrosDeAcompanhamentoRegistroRetornoV1 {

    @Schema(description = "Identificador público do registro")
    private Long                        uid;
    private TipoRegistroAcompanhamento  tipo;
    private LocalDate                   dataRegistro;
    private String                      descricao;
    private String                      autor;
}
