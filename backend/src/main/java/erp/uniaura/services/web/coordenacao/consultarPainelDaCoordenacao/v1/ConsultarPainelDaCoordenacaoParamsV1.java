package erp.uniaura.services.web.coordenacao.consultarPainelDaCoordenacao.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultarPainelDaCoordenacaoParamsV1 {

    @Schema(description = "Identificador público do curso; sem valor, consolida todos os cursos")
    private Long cursoUid;

    @Schema(description = "Período letivo, por exemplo 2026.1; sem valor, consolida todos os períodos")
    private String periodoLetivo;
}
