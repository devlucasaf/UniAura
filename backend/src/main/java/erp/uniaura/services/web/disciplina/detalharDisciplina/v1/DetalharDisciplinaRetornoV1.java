package erp.uniaura.services.web.disciplina.detalharDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharDisciplinaRetornoV1 {

    @Schema(description = "Identificador público da disciplina")
    private Long          uid;

    @Schema(description = "TRANSITÓRIO: id sequencial, ainda exigido por Turma e Professor; sai quando eles migrarem")
    private Long          id;

    private String        codigo;
    private String        nome;
    private String        ementa;
    private Integer       cargaHoraria;
    private Long          cursoUid;
    private String        cursoNome;
    private Integer       periodo;
    private Boolean       ativo;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
