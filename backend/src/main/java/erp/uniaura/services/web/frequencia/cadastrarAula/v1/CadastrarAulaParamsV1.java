package erp.uniaura.services.web.frequencia.cadastrarAula.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CadastrarAulaParamsV1 {

    @Schema(description = "Identificador público do vínculo entre turma e disciplina")
    @NotNull(message = "O uid do vínculo entre turma e disciplina é obrigatório.")
    private Long turmaDisciplinaUid;

    @Schema(description = "Data em que a aula foi dada")
    @NotNull(message = "A data da aula é obrigatória.")
    private LocalDate dataAula;

    @Schema(description = "Conteúdo ministrado na aula")
    @Size(max = 4000, message = "O conteúdo ministrado deve ter no máximo 4000 caracteres.")
    private String conteudoMinistrado;

    @Schema(description = "Identificador público do professor que deu a aula; sem valor, usa o professor do vínculo")
    private Long professorUid;
}
