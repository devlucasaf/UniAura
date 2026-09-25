package erp.uniaura.modules.coordenacao.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrarAtaRequestDTO {

    @NotNull
    @Size(min = 20, max = 8000)
    private String              deliberacoes;

    // --- PRESENÇAS DOS CONVOCADOS; OS NÃO INFORMADOS FICAM COMO AUSENTES ---
    @Valid
    private List<PresencaDTO>   presencas;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PresencaDTO {

        @NotNull
        private UUID    usuarioId;

        @NotNull
        private Boolean presente;
    }
}
