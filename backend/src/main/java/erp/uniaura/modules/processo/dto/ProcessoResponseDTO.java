package erp.uniaura.modules.processo.dto;

import erp.uniaura.modules.processo.model.StatusProcesso;
import erp.uniaura.modules.processo.model.TipoProcesso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessoResponseDTO {
    private Long                                    id;
    private String                                  protocolo;
    private Long                                    alunoId;
    private String                                  alunoNome;
    private String                                  alunoMatriculaRA;
    private TipoProcesso                            tipo;
    private String                                  assunto;
    private String                                  descricao;
    private StatusProcesso                          status;
    private Long                                    responsavelId;
    private String                                  responsavelNome;
    private LocalDate                               prazoResposta;
    private String                                  parecerFinal;
    private LocalDateTime                           encerradoEm;
    private LocalDateTime                           criadoEm;
    private LocalDateTime                           atualizadoEm;
    private List<MovimentacaoProcessoResponseDTO>   movimentacoes;
}
