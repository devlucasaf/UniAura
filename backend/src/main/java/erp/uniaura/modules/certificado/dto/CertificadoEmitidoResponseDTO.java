package erp.uniaura.modules.certificado.dto;

import erp.uniaura.modules.certificado.model.TipoCertificado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificadoEmitidoResponseDTO {

    private Long            id;
    private Long            alunoId;
    private String          alunoNome;
    private TipoCertificado tipo;
    private String          codigoVerificacao;
    private String          emitidoPorNome;
    private LocalDateTime   emitidoEm;
}
