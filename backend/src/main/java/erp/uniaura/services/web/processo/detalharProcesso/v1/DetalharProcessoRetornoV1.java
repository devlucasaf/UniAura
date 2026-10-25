package erp.uniaura.services.web.processo.detalharProcesso.v1;

import erp.uniaura.persistence.entities.StatusProcesso;
import erp.uniaura.persistence.entities.TipoProcesso;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DetalharProcessoRetornoV1 {

    @Schema(description = "Identificador público do processo")
    private Long                                            uid;
    private String                                          protocolo;
    private String                                          alunoNome;
    private String                                          alunoMatriculaRA;
    private TipoProcesso                                    tipo;
    private String                                          assunto;
    private String                                          descricao;
    private StatusProcesso                                  status;
    private String                                          responsavelNome;
    private LocalDate                                       prazoResposta;
    private String                                          parecerFinal;
    private LocalDateTime                                   encerradoEm;
    private LocalDateTime                                   criadoEm;
    private LocalDateTime                                   atualizadoEm;
    private List<DetalharProcessoMovimentacaoRetornoV1>     movimentacoes;
}
