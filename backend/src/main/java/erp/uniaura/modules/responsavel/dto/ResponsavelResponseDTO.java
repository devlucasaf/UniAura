package erp.uniaura.modules.responsavel.dto;

import erp.uniaura.modules.responsavel.model.Parentesco;
import erp.uniaura.modules.usuario.dto.UsuarioResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsavelResponseDTO {

    private UUID                id;
    private UsuarioResponseDTO  usuario;
    private Parentesco          parentesco;
    private LocalDateTime       criadoEm;
    private LocalDateTime       atualizadoEm;
}

