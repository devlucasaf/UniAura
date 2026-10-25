package erp.uniaura.services.web.responsavel.pesquisarAlunosDoResponsavel.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarAlunosDoResponsavelAlunoRetornoV1 {

    @Schema(description = "Identificador público do aluno")
    private Long            alunoUid;
    private String          nome;
    private String          matriculaRA;
    private String          observacao;
    private LocalDateTime   vinculadoEm;
}
