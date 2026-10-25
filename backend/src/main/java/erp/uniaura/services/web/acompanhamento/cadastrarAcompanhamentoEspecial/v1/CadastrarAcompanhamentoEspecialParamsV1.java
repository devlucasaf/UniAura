package erp.uniaura.services.web.acompanhamento.cadastrarAcompanhamentoEspecial.v1;

import erp.uniaura.persistence.entities.TipoNecessidadeEspecial;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CadastrarAcompanhamentoEspecialParamsV1 {

    @Schema(description = "Identificador público do aluno acompanhado")
    @NotNull(message = "O uid do aluno é obrigatório.")
    private Long alunoUid;

    @Schema(description = "Tipo da necessidade especial")
    @NotNull(message = "O tipo da necessidade é obrigatório.")
    private TipoNecessidadeEspecial tipoNecessidade;

    @Schema(description = "Descrição da necessidade (dado sensível: acesso restrito ao departamento)")
    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.")
    private String descricaoNecessidade;

    @Schema(description = "Recursos e adaptações de que o aluno precisa (ex.: intérprete de Libras, prova ampliada)")
    @Size(max = 1000, message = "Os recursos necessários devem ter no máximo 1000 caracteres.")
    private String recursosNecessarios;

    @Schema(description = "Indica se o aluno entregou laudo", defaultValue = "false")
    private Boolean possuiLaudo = false;

    @Schema(description = "Data de início do acompanhamento; se vazia, usa a data de hoje")
    private LocalDate dataInicio;
}
