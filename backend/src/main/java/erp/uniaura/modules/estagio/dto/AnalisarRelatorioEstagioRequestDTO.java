package erp.uniaura.modules.estagio.dto;

import erp.uniaura.modules.estagio.model.StatusRelatorioEstagio;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisarRelatorioEstagioRequestDTO {

    @NotNull(message = "O status da análise é obrigatório.")
    private StatusRelatorioEstagio status;

    @Size(max = 500)
    private String observacoes;
}
