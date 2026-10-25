package erp.uniaura.services.web.biblioteca.pesquisarExemplaresDoLivro.v1;

import erp.uniaura.persistence.entities.StatusExemplar;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarExemplaresDoLivroExemplarRetornoV1 {

    @Schema(description = "Identificador público do exemplar")
    private Long            uid;
    private String          codigoBarras;
    private String          localizacao;
    private StatusExemplar  status;
    private LocalDateTime   criadoEm;
}
