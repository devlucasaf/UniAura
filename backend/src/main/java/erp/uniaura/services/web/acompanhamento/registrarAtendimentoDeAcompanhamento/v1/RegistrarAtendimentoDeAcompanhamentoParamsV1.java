package erp.uniaura.services.web.acompanhamento.registrarAtendimentoDeAcompanhamento.v1;

import erp.uniaura.persistence.entities.TipoRegistroAcompanhamento;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegistrarAtendimentoDeAcompanhamentoParamsV1 {

    @Schema(description = "Identificador público do acompanhamento")
    @NotNull(message = "O uid do acompanhamento é obrigatório.")
    private Long acompanhamentoUid;

    @Schema(description = "Tipo do registro")
    @NotNull(message = "O tipo do registro é obrigatório.")
    private TipoRegistroAcompanhamento tipo;

    @Schema(description = "Data do atendimento; se vazia, usa a data de hoje")
    private LocalDate dataRegistro;

    @Schema(description = "O que foi feito, combinado ou observado")
    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres.")
    private String descricao;
}
