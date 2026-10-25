package erp.uniaura.services.web.autenticacao.consultarUsuarioLogado.v1;

import erp.uniaura.persistence.entities.TipoUsuario;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ConsultarUsuarioLogadoRetornoV1 {

    @Schema(description = "Identificador público do usuário")
    private Long        uid;
    private String      nome;
    private String      email;
    private String      cpf;
    private String      telefone;
    private LocalDate   dataNascimento;
    private TipoUsuario role;
}
