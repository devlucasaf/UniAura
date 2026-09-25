package erp.uniaura.modules.processo.dto;

import erp.uniaura.modules.processo.model.TipoProcesso;

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
public class ProcessoRequestDTO {

    @NotNull
    private TipoProcesso tipo;

    @NotNull
    @Size(min = 5, max = 200)
    private String assunto;

    @NotNull
    @Size(min = 20, max = 4000)
    private String descricao;
}
