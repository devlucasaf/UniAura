package erp.uniaura.services.web.biblioteca.registrarEmprestimo.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegistrarEmprestimoRetornoV1 {

    @Schema(description = "Identificador público do empréstimo")
    private Long            uid;
    private LocalDateTime   dataDevolucaoPrevista;
}
