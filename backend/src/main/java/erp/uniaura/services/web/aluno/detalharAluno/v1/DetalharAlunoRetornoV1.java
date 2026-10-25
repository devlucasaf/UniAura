package erp.uniaura.services.web.aluno.detalharAluno.v1;

import erp.uniaura.persistence.entities.StatusAluno;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharAlunoRetornoV1 {

    @Schema(description = "Identificador público do aluno")
    private Long          uid;
    private String        nome;
    private String        email;
    private String        cpf;
    private String        telefone;
    private LocalDate     dataNascimento;
    private String        matriculaRA;
    private LocalDate     dataIngresso;
    private StatusAluno   status;
    private String        curso;

    @Schema(description = "Identificador público da turma atual; vazio se o aluno não está em turma")
    private Long          turmaAtualUid;
    private String        observacoes;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
