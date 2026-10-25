package erp.uniaura.services.web.documento.pesquisarMeusDocumentos.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarMeusDocumentosRetornoV1 {

    @Schema(description = "Total de documentos do aluno, ignorando a paginação")
    private Long total;

    @Schema(description = "Documentos da página pedida")
    private List<PesquisarMeusDocumentosDocumentoRetornoV1> lista;
}
