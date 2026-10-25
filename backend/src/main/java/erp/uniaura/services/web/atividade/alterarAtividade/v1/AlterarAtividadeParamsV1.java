package erp.uniaura.services.web.atividade.alterarAtividade.v1;

import erp.uniaura.persistence.entities.TipoAtividade;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AlterarAtividadeParamsV1 {

    @Schema(description = "Identificador público da atividade a alterar")
    @NotNull(message = "O uid da atividade é obrigatório.")
    private Long uid;

    @Schema(description = "Identificador público do vínculo entre turma e disciplina")
    @NotNull(message = "O uid do vínculo entre turma e disciplina é obrigatório.")
    private Long turmaDisciplinaUid;

    @Schema(description = "Título da atividade")
    @NotNull(message = "O título é obrigatório.")
    @Size(min = 3, max = 200, message = "O título deve ter entre 3 e 200 caracteres.")
    private String titulo;

    @Schema(description = "Descrição e instruções da atividade")
    @Size(max = 4000, message = "A descrição deve ter no máximo 4000 caracteres.")
    private String descricao;

    @Schema(description = "Tipo da atividade")
    @NotNull(message = "O tipo da atividade é obrigatório.")
    private TipoAtividade tipo;

    @Schema(description = "Prazo de entrega; não pode ser anterior à data de postagem")
    @NotNull(message = "A data de entrega é obrigatória.")
    private LocalDateTime dataEntrega;

    @Schema(description = "Nota máxima da atividade")
    @NotNull(message = "O valor máximo é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor máximo deve ser maior que zero.")
    private BigDecimal valorMaximo;

    @Schema(description = "Indica se a atividade aceita entregas; sem valor, mantém o atual")
    private Boolean ativa;
}
