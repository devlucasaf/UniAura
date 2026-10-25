package erp.uniaura.services.web.estagio.enviarRelatorioDeEstagio.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnviarRelatorioDeEstagioParamsV1 {

    @Schema(description = "Identificador público do estágio")
    @NotNull(message = "O uid do estágio é obrigatório.")
    private Long estagioUid;

    @Schema(description = "Mês de referência do relatório, no formato AAAA-MM (ex.: 2026-03)")
    @NotBlank(message = "O período de referência é obrigatório.")
    @Size(max = 7, message = "O período de referência deve ter no máximo 7 caracteres.")
    private String periodoReferencia;

    @Schema(description = "Horas cumpridas no período")
    @NotNull(message = "As horas registradas são obrigatórias.")
    @Positive(message = "As horas registradas devem ser maiores que zero.")
    private Integer horasRegistradas;

    @Schema(description = "Descrição das atividades feitas no período")
    @Size(max = 2000, message = "A descrição das atividades deve ter no máximo 2000 caracteres.")
    private String descricaoAtividades;
}
