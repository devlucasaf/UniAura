package erp.uniaura.services.web.ouvidoria.registrarManifestacao.v1;

import erp.uniaura.persistence.entities.TipoManifestacao;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarManifestacaoParamsV1 {

    @Schema(description = "Tipo da manifestação (reclamação, denúncia, sugestão ou elogio)")
    @NotNull(message = "O tipo da manifestação é obrigatório.")
    private TipoManifestacao tipo;

    @Schema(description = "Assunto resumido")
    @NotNull(message = "O assunto é obrigatório.")
    @Size(min = 5, max = 200, message = "O assunto deve ter entre 5 e 200 caracteres.")
    private String assunto;

    @Schema(description = "Descrição completa da manifestação")
    @NotNull(message = "A descrição é obrigatória.")
    @Size(min = 20, max = 4000, message = "A descrição deve ter entre 20 e 4000 caracteres.")
    private String descricao;

    @Schema(description = "Setor a que a manifestação se refere")
    @Size(max = 120, message = "O setor deve ter no máximo 120 caracteres.")
    private String setor;

    @Schema(description = "Quando verdadeiro, o nome do autor não aparece para a equipe da ouvidoria", defaultValue = "false")
    private Boolean anonima = false;
}
