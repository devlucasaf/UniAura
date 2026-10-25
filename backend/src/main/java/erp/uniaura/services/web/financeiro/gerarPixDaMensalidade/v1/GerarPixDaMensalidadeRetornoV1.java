package erp.uniaura.services.web.financeiro.gerarPixDaMensalidade.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class GerarPixDaMensalidadeRetornoV1 {

    @Schema(description = "Código Pix copia e cola (fictício)")
    private String        payload;

    @Schema(description = "Valor a pagar, já com a multa se a mensalidade estiver atrasada")
    private BigDecimal    valor;

    @Schema(description = "Momento em que o código deixa de valer")
    private LocalDateTime expiraEm;
}
