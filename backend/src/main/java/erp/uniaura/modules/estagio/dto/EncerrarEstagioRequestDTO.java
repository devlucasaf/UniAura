package erp.uniaura.modules.estagio.dto;

import erp.uniaura.modules.estagio.model.StatusEstagio;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncerrarEstagioRequestDTO {

    @NotNull(message = "O status de encerramento é obrigatório.")
    private StatusEstagio status;

    @NotNull(message = "A data de fim efetiva é obrigatória.")
    private LocalDate dataFimEfetiva;
}
