package erp.uniaura.modules.ouvidoria.dto;

import erp.uniaura.modules.ouvidoria.model.TipoManifestacao;

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
public class ManifestacaoRequestDTO {

    @NotNull
    private TipoManifestacao tipo;

    @NotNull
    @Size(min = 5, max = 200)
    private String assunto;

    @NotNull
    @Size(min = 20, max = 4000)
    private String descricao;

    @Size(max = 120)
    private String setor;

    private Boolean anonima;
}
