package erp.uniaura.modules.usuario.dto;

import erp.uniaura.modules.usuario.model.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {

    private UUID            id;
    private String          nome;
    private String          email;
    private String          cpf;
    private String          telefone;
    private LocalDate       dataNascimento;
    private Boolean         ativo;
    private TipoUsuario     role;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}

