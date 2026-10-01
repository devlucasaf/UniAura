package erp.uniaura.modules.processo.dto;

import erp.uniaura.modules.processo.model.StatusProcesso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoProcessoResponseDTO {

    private Long            id;
    private String          autorNome;
    private StatusProcesso  statusAnterior;
    private StatusProcesso  statusNovo;
    private String          comentario;
    private Boolean         visivelParaAluno;
    private LocalDateTime   criadoEm;
}
