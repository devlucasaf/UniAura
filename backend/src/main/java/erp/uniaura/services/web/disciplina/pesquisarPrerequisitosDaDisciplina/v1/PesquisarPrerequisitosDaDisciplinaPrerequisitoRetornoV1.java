package erp.uniaura.services.web.disciplina.pesquisarPrerequisitosDaDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarPrerequisitosDaDisciplinaPrerequisitoRetornoV1 {

    @Schema(description = "Identificador público do vínculo de pré-requisito")
    private Long          uid;

    @Schema(description = "Identificador público da disciplina que é pré-requisito")
    private Long          prerequisitoUid;

    private String        prerequisitoCodigo;
    private String        prerequisitoNome;
    private LocalDateTime criadoEm;
}
