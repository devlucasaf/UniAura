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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrarAtaRequestDTO {

    @NotNull
    @Size(min = 20, max = 8000)
    private String              deliberacoes;

    @Valid
    private List<PresencaDTO>   presencas;


}
