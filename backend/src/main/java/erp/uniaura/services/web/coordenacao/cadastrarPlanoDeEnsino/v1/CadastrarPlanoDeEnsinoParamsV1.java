package erp.uniaura.services.web.coordenacao.cadastrarPlanoDeEnsino.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarPlanoDeEnsinoParamsV1 {

    @Schema(description = "Identificador público do vínculo entre turma e disciplina")
    @NotNull(message = "O uid do vínculo entre turma e disciplina é obrigatório.")
    private Long turmaDisciplinaUid;

    @Schema(description = "Ementa, de 20 a 4000 caracteres")
    @NotNull(message = "A ementa é obrigatória.")
    @Size(min = 20, max = 4000, message = "A ementa deve ter de 20 a 4000 caracteres.")
    private String ementa;

    @Schema(description = "Objetivos, de 20 a 4000 caracteres")
    @NotNull(message = "Os objetivos são obrigatórios.")
    @Size(min = 20, max = 4000, message = "Os objetivos devem ter de 20 a 4000 caracteres.")
    private String objetivos;

    @Schema(description = "Conteúdo programático, de 20 a 8000 caracteres")
    @NotNull(message = "O conteúdo programático é obrigatório.")
    @Size(min = 20, max = 8000, message = "O conteúdo programático deve ter de 20 a 8000 caracteres.")
    private String conteudoProgramatico;

    @Schema(description = "Metodologia, de até 4000 caracteres")
    @Size(max = 4000, message = "A metodologia deve ter no máximo 4000 caracteres.")
    private String metodologia;

    @Schema(description = "Critérios de avaliação, de 20 a 4000 caracteres")
    @NotNull(message = "Os critérios de avaliação são obrigatórios.")
    @Size(min = 20, max = 4000, message = "Os critérios de avaliação devem ter de 20 a 4000 caracteres.")
    private String criteriosAvaliacao;

    @Schema(description = "Bibliografia básica, de 10 a 4000 caracteres")
    @NotNull(message = "A bibliografia básica é obrigatória.")
    @Size(min = 10, max = 4000, message = "A bibliografia básica deve ter de 10 a 4000 caracteres.")
    private String bibliografiaBasica;

    @Schema(description = "Bibliografia complementar, de até 4000 caracteres")
    @Size(max = 4000, message = "A bibliografia complementar deve ter no máximo 4000 caracteres.")
    private String bibliografiaComplementar;
}
