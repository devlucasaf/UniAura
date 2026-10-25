package erp.uniaura.services.web.ouvidoria.pesquisarManifestacoes.v1;

import erp.uniaura.persistence.entities.StatusManifestacao;
import erp.uniaura.persistence.entities.TipoManifestacao;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarManifestacoesManifestacaoRetornoV1 {

    @Schema(description = "Identificador público da manifestação")
    private Long                uid;

    private String              protocolo;
    private TipoManifestacao    tipo;
    private String              assunto;
    private String              setor;
    private StatusManifestacao  status;

    @Schema(description = "Nulo quando a manifestação é anônima")
    private String              autorNome;

    private String              responsavelNome;
    private LocalDate           prazoResposta;
    private LocalDateTime       criadoEm;
}
