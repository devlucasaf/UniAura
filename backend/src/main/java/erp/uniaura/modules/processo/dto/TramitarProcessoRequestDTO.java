package erp.uniaura.modules.processo.dto;

import erp.uniaura.modules.processo.model.StatusProcesso;

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
public class TramitarProcessoRequestDTO {
    @NotNull
    private StatusProcesso  status;

    @Size(max = 4000)
    private String          comentario;
    private LocalDate       prazoResposta;
    private Boolean         visivelParaAluno;
}
