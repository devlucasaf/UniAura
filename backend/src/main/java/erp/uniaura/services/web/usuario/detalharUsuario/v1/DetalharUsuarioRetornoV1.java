package erp.uniaura.services.web.usuario.detalharUsuario.v1;

import erp.uniaura.persistence.entities.TipoUsuario;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharUsuarioRetornoV1 {

    @Schema(description = "Identificador público do usuário")
    private Long          uid;
    private String        nome;
    private String        email;
    private String        cpf;
    private String        telefone;
    private LocalDate     dataNascimento;
    private Boolean       ativo;
    private TipoUsuario   role;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
