package erp.uniaura.modules.tcc.dto;

import erp.uniaura.modules.tcc.model.StatusEntregaTcc;

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
public class AnalisarEntregaTccRequestDTO {

    @NotNull(message = "O status da análise é obrigatório.")
    private StatusEntregaTcc status;

    @Size(max = 1000)
    private String observacoesOrientador;
}
