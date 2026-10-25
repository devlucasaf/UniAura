package erp.uniaura.services.web.ouvidoria.detalharManifestacao.v1;

import erp.uniaura.persistence.entities.StatusManifestacao;
import erp.uniaura.persistence.entities.TipoManifestacao;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DetalharManifestacaoRetornoV1 {

    @Schema(description = "Identificador público da manifestação")
    private Long                                            uid;
    private String                                          protocolo;
    private TipoManifestacao                                tipo;
    private String                                          assunto;
    private String                                          descricao;
    private String                                          setor;
    private StatusManifestacao                              status;
    private Boolean                                         anonima;

    @Schema(description = "Nulo quando a manifestação é anônima")
    private String                                          autorNome;
    private String                                          responsavelNome;
    private LocalDate                                       prazoResposta;
    private String                                          respostaFinal;
    private LocalDateTime                                   respondidoEm;
    private LocalDateTime                                   encerradoEm;
    private LocalDateTime                                   criadoEm;
    private LocalDateTime                                   atualizadoEm;
    private List<DetalharManifestacaoRespostaRetornoV1>     respostas;
}
