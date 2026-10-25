package erp.uniaura.services.web.documento.pesquisarMeusDocumentos.v1;

import erp.uniaura.persistence.entities.StatusDocumento;
import erp.uniaura.persistence.entities.TipoDocumento;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarMeusDocumentosDocumentoRetornoV1 {

    @Schema(description = "Identificador público do documento")
    private Long            uid;
    private TipoDocumento   tipo;
    private String          nomeArquivo;
    private String          arquivoUrl;
    private StatusDocumento status;
    private Integer         versao;
    private String          observacoes;
    private String          analisadoPorNome;
    private LocalDateTime   analisadoEm;
    private LocalDateTime   enviadoEm;
}
