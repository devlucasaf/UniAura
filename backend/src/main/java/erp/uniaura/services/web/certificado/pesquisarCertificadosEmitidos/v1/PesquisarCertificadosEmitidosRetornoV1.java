package erp.uniaura.services.web.certificado.pesquisarCertificadosEmitidos.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarCertificadosEmitidosRetornoV1 {

    @Schema(description = "Total de emissões do aluno, ignorando a paginação")
    private Long total;

    @Schema(description = "Emissões da página pedida")
    private List<PesquisarCertificadosEmitidosCertificadoRetornoV1> lista;
}
