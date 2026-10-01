package erp.uniaura.modules.atividade.dto;

import erp.uniaura.modules.atividade.model.TipoAtividade;

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
public class AtividadeResponseDTO {

    private Long            id;
    private Long            turmaDisciplinaId;
    private String          titulo;
    private String          descricao;
    private TipoAtividade   tipo;
    private LocalDateTime   dataPostagem;
    private LocalDateTime   dataEntrega;
    private BigDecimal      valorMaximo;
    private Long            professorId;
    private String          professorNome;
    private Boolean         ativa;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}

