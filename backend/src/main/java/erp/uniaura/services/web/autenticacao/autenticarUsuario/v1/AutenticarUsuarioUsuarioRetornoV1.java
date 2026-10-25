package erp.uniaura.services.web.autenticacao.autenticarUsuario.v1;

import erp.uniaura.persistence.entities.TipoUsuario;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticarUsuarioUsuarioRetornoV1 {

    @Schema(description = "Identificador público do usuário")
    private Long        uid;
    private String      nome;
    private String      email;
    private TipoUsuario role;
}
