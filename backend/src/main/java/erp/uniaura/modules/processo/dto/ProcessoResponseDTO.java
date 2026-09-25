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
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessoResponseDTO {
    private UUID                                    id;
    private String                                  protocolo;
    private UUID                                    alunoId;
    private String                                  alunoNome;
    private String                                  alunoMatriculaRA;
    private TipoProcesso                            tipo;
    private String                                  assunto;
    private String                                  descricao;
    private StatusProcesso                          status;
    private UUID                                    responsavelId;
    private String                                  responsavelNome;
    private LocalDate                               prazoResposta;
    private String                                  parecerFinal;
    private LocalDateTime                           encerradoEm;
    private LocalDateTime                           criadoEm;
    private LocalDateTime                           atualizadoEm;
    private List<MovimentacaoProcessoResponseDTO>   movimentacoes;
}
