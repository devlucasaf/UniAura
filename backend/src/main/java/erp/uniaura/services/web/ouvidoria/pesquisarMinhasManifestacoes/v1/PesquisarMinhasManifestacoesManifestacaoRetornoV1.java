package erp.uniaura.services.web.ouvidoria.pesquisarMinhasManifestacoes.v1;

import erp.uniaura.persistence.entities.StatusManifestacao;
import erp.uniaura.persistence.entities.TipoManifestacao;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarMinhasManifestacoesManifestacaoRetornoV1 {

    @Schema(description = "Identificador público da manifestação")
    private Long                uid;
    private String              protocolo;
    private TipoManifestacao    tipo;
    private String              assunto;
    private String              setor;
    private StatusManifestacao  status;
    private Boolean             anonima;
    private LocalDate           prazoResposta;
    private LocalDateTime       criadoEm;
}
