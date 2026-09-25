package erp.uniaura.modules.funcionario.dto;

import erp.uniaura.modules.funcionario.model.CargoFuncionario;
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
public class FuncionarioResponseDTO {

    private UUID                id;
    private UsuarioResponseDTO  usuario;
    private CargoFuncionario    cargo;
    private LocalDate           dataAdmissao;
    private String              departamento;
    private LocalDateTime       criadoEm;
    private LocalDateTime       atualizadoEm;
}

