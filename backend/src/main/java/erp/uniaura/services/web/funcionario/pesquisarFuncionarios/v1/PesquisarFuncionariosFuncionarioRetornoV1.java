package erp.uniaura.services.web.funcionario.pesquisarFuncionarios.v1;

import erp.uniaura.persistence.entities.CargoFuncionario;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PesquisarFuncionariosFuncionarioRetornoV1 {

    @Schema(description = "Identificador público do funcionário")
    private Long                uid;
    private String              nome;
    private String              email;
    private CargoFuncionario    cargo;
    private LocalDate           dataAdmissao;
    private String              departamento;
}
