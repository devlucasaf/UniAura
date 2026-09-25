package erp.uniaura.dto.auth;

import erp.uniaura.modules.usuario.dto.UsuarioResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {

    private String              token;
    private String              refreshToken;
    private UsuarioResponseDTO  usuario;
}

