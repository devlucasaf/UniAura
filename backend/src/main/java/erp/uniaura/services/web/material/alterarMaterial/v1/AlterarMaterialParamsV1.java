package erp.uniaura.services.web.material.alterarMaterial.v1;

import erp.uniaura.persistence.entities.TipoMaterial;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarMaterialParamsV1 {

    @Schema(description = "Identificador público do material a alterar")
    @NotNull(message = "O uid do material é obrigatório.")
    private Long uid;

    @Schema(description = "Identificador público do vínculo entre a turma e a disciplina")
    @NotNull(message = "O vínculo entre turma e disciplina é obrigatório.")
    private Long turmaDisciplinaUid;

    @Schema(description = "Título do material")
    @NotNull(message = "O título é obrigatório.")
    @Size(min = 3, max = 200, message = "O título deve ter entre 3 e 200 caracteres.")
    private String titulo;

    @Schema(description = "Descrição do material")
    @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres.")
    private String descricao;

    @Schema(description = "Tipo do material; LINK usa 'linkUrl', os demais exigem um arquivo (o novo ou o que já existe)")
    @NotNull(message = "O tipo é obrigatório.")
    private TipoMaterial tipo;

    @Schema(description = "Endereço do material, obrigatório quando o tipo é LINK")
    @Size(max = 500, message = "O link deve ter no máximo 500 caracteres.")
    private String linkUrl;
}
