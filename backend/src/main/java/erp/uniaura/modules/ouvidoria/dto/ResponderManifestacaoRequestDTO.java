package erp.uniaura.modules.ouvidoria.dto;

import erp.uniaura.modules.ouvidoria.model.StatusManifestacao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
public class ResponderManifestacaoRequestDTO {

    @NotNull
    private StatusManifestacao status;

    @NotNull
    @Size(min = 5, max = 4000)
    private String mensagem;

    private LocalDate prazoResposta;

    private Boolean visivelParaAutor;
}
