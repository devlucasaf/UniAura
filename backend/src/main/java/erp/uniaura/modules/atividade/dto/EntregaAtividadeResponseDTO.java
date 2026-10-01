package erp.uniaura.modules.atividade.dto;

import erp.uniaura.modules.atividade.model.StatusEntrega;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntregaAtividadeResponseDTO {

    private Long            id;
    private Long            atividadeId;
    private String          atividadeTitulo;
    private Long            alunoId;
    private String          alunoNome;
    private String          arquivoUrl;
    private String          comentarioAluno;
    private LocalDateTime   dataEntrega;
    private BigDecimal      nota;
    private String          feedback;
    private StatusEntrega   status;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}

