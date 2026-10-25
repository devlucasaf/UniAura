package erp.uniaura.services.web.documento.pesquisarDocumentosDoAluno.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarDocumentosDoAlunoRetornoV1 {

    @Schema(description = "Total de documentos que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Documentos da página pedida")
    private List<PesquisarDocumentosDoAlunoDocumentoRetornoV1> lista;
}
