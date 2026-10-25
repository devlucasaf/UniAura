package erp.uniaura.services.web.frequencia.cadastrarAula.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarAulaRetornoV1 {

    @Schema(description = "Identificador público da aula")
    private Long uid;

    @Schema(description = "Quantidade de alunos com matrícula ATIVA que receberam registro de presença")
    private Integer totalAlunos;
}
