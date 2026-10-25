package erp.uniaura.services.web.acompanhamento.alterarAcompanhamentoEspecial.v1;

import erp.uniaura.persistence.entities.StatusAcompanhamento;
import erp.uniaura.persistence.entities.TipoNecessidadeEspecial;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarAcompanhamentoEspecialParamsV1 {

    @Schema(description = "Identificador público do acompanhamento a alterar")
    @NotNull(message = "O uid do acompanhamento é obrigatório.")
    private Long uid;

    @Schema(description = "Tipo da necessidade especial")
    @NotNull(message = "O tipo da necessidade é obrigatório.")
    private TipoNecessidadeEspecial tipoNecessidade;

    @Schema(description = "Descrição da necessidade (dado sensível)")
    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.")
    private String descricaoNecessidade;

    @Schema(description = "Recursos e adaptações de que o aluno precisa")
    @Size(max = 1000, message = "Os recursos necessários devem ter no máximo 1000 caracteres.")
    private String recursosNecessarios;

    @Schema(description = "Indica se o aluno entregou laudo")
    @NotNull(message = "Informe se o aluno possui laudo.")
    private Boolean possuiLaudo;

    @Schema(description = "Novo status; ao encerrar, a data de encerramento passa a ser hoje")
    @NotNull(message = "O status é obrigatório.")
    private StatusAcompanhamento status;
}
