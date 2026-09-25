package erp.uniaura.modules.coordenacao.dto;

import erp.uniaura.modules.coordenacao.model.StatusPlanoEnsino;

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
public class AvaliarPlanoEnsinoRequestDTO {

    // --- ACEITA APENAS APROVADO, DEVOLVIDO OU REPROVADO ---
    @NotNull
    private StatusPlanoEnsino   status;

    @Size(max = 4000)
    private String              parecer;
}
