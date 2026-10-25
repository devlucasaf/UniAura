package erp.uniaura.services.web.material.detalharMaterial.v1;

import erp.uniaura.persistence.entities.TipoMaterial;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharMaterialRetornoV1 {

    @Schema(description = "Identificador público do material")
    private Long            uid;

    @Schema(description = "Identificador público do vínculo entre a turma e a disciplina")
    private Long            turmaDisciplinaUid;
    private String          titulo;
    private String          descricao;
    private TipoMaterial    tipo;
    private String          arquivoUrl;
    private String          linkUrl;

    @Schema(description = "Identificador público do professor responsável")
    private Long            professorUid;
    private String          professorNome;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}
