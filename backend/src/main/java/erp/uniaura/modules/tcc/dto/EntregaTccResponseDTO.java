package erp.uniaura.modules.tcc.dto;

import erp.uniaura.modules.tcc.model.StatusEntregaTcc;
import erp.uniaura.modules.tcc.model.TipoEntregaTcc;

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
public class EntregaTccResponseDTO {

    private Long                id;
    private Long                tccId;
    private TipoEntregaTcc      tipo;
    private String              arquivoUrl;
    private StatusEntregaTcc    status;
    private String              observacoesOrientador;
    private LocalDateTime       analisadoEm;
    private LocalDateTime       enviadoEm;
}
