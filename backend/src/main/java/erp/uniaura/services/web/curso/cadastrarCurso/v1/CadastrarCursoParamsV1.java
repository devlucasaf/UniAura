package erp.uniaura.services.web.curso.cadastrarCurso.v1;

import erp.uniaura.persistence.entities.NivelCurso;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarCursoParamsV1 {

    @Schema(description = "Nome do curso")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    private String nome;

    @Schema(description = "Descrição do curso")
    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.")
    private String descricao;

    @Schema(description = "Nível do curso")
    @NotNull(message = "O nível é obrigatório.")
    private NivelCurso nivel;

    @Schema(description = "Duração, em semestres")
    @NotNull(message = "A duração (semestres) é obrigatória.")
    @Positive(message = "A duração deve ser positiva.")
    private Integer duracaoSemestres;

    @Schema(description = "Carga horária total, em horas")
    @NotNull(message = "A carga horária total é obrigatória.")
    @Positive(message = "A carga horária deve ser positiva.")
    private Integer cargaHorariaTotal;

    @Schema(description = "Se o curso está ativo; sem valor, é cadastrado como ativo")
    private Boolean ativo;
}
