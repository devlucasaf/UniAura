package erp.uniaura.services.web.certificado.pesquisarCertificadosEmitidos.v1;

import erp.uniaura.persistence.entities.TipoCertificado;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarCertificadosEmitidosCertificadoRetornoV1 {

    @Schema(description = "Identificador público da emissão")
    private Long            uid;
    private TipoCertificado tipo;
    private String          codigoVerificacao;
    private String          emitidoPorNome;
    private LocalDateTime   emitidoEm;
}
