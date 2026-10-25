package erp.uniaura.services.web.funcionario.detalharFuncionario.v1;

import erp.uniaura.persistence.entities.CargoFuncionario;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharFuncionarioRetornoV1 {

    @Schema(description = "Identificador público do funcionário")
    private Long                uid;
    private String              nome;
    private String              email;
    private String              cpf;
    private String              telefone;
    private LocalDate           dataNascimento;
    private CargoFuncionario    cargo;
    private LocalDate           dataAdmissao;
    private String              departamento;
    private LocalDateTime       criadoEm;
    private LocalDateTime       atualizadoEm;
}
