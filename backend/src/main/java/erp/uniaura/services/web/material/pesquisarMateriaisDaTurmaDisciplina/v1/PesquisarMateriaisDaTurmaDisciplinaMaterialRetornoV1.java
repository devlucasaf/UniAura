package erp.uniaura.services.web.material.pesquisarMateriaisDaTurmaDisciplina.v1;

import erp.uniaura.persistence.entities.TipoMaterial;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarMateriaisDaTurmaDisciplinaMaterialRetornoV1 {

    @Schema(description = "Identificador público do material")
    private Long            uid;
    private String          titulo;
    private String          descricao;
    private TipoMaterial    tipo;
    private String          arquivoUrl;
    private String          linkUrl;
    private String          professorNome;
    private LocalDateTime   criadoEm;
}
