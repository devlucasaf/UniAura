package erp.uniaura.modules.professor.dto;

import erp.uniaura.modules.usuario.dto.UsuarioResponseDTO;

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
public class ProfessorResponseDTO {

    private UUID                id;
    private UsuarioResponseDTO  usuario;
    private String              formacao;
    private String              areaAtuacao;
    private Integer             cargaHorariaSemanal;
    private LocalDate           dataAdmissao;
    private Boolean             ativo;
    private LocalDateTime       criadoEm;
    private LocalDateTime       atualizadoEm;
}

