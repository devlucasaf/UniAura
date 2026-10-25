package erp.uniaura.services.web.matricula.trancarMatricula.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrancarMatriculaParamsV1 {

    @Schema(description = "Identificador público da matrícula")
    @NotNull(message = "O uid da matrícula é obrigatório.")
    private Long uid;

    @Schema(description = "Motivo do trancamento; sem valor, mantém as observações atuais")
    @Size(max = 1000, message = "As observações devem ter no máximo 1000 caracteres.")
    private String observacoes;
}
