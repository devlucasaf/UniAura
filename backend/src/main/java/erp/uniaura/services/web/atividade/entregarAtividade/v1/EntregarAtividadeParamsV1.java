package erp.uniaura.services.web.atividade.entregarAtividade.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregarAtividadeParamsV1 {

    @Schema(description = "Identificador público da atividade entregue")
    @NotNull(message = "O uid da atividade é obrigatório.")
    private Long atividadeUid;

    @Schema(description = "Comentário do aluno para o professor")
    @Size(max = 2000, message = "O comentário deve ter no máximo 2000 caracteres.")
    private String comentarioAluno;
}
