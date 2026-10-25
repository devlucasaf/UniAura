package erp.uniaura.services.web.tcc.pesquisarMembrosDaBanca.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PesquisarMembrosDaBancaMembroRetornoV1 {

    @Schema(description = "Identificador público do membro da banca")
    private Long       uid;

    @Schema(description = "Identificador público do professor")
    private Long       professorUid;

    private String     professorNome;
    private BigDecimal nota;
    private String     parecer;
}
