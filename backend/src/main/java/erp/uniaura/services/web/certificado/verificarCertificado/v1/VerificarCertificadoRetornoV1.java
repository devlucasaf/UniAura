package erp.uniaura.services.web.certificado.verificarCertificado.v1;

import erp.uniaura.persistence.entities.TipoCertificado;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VerificarCertificadoRetornoV1 {
    private String          alunoNome;
    private TipoCertificado tipo;
    private String          codigoVerificacao;
    private String          emitidoPorNome;
    private LocalDateTime   emitidoEm;
}
