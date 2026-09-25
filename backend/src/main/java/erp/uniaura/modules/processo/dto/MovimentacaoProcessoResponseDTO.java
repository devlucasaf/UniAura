package erp.uniaura.modules.processo.dto;

import erp.uniaura.modules.processo.model.StatusProcesso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoProcessoResponseDTO {

    private UUID            id;
    private String          autorNome;
    private StatusProcesso  statusAnterior;
    private StatusProcesso  statusNovo;
    private String          comentario;
    private Boolean         visivelParaAluno;
    private LocalDateTime   criadoEm;
}
