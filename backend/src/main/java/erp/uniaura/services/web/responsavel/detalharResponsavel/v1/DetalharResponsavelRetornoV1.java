package erp.uniaura.services.web.responsavel.detalharResponsavel.v1;

import erp.uniaura.persistence.entities.Parentesco;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharResponsavelRetornoV1 {

    @Schema(description = "Identificador público do responsável")
    private Long            uid;
    private String          nome;
    private String          email;
    private String          cpf;
    private String          telefone;
    private LocalDate       dataNascimento;
    private Parentesco      parentesco;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}
