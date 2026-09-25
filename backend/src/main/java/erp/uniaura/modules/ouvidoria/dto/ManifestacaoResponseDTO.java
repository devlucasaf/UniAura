package erp.uniaura.modules.ouvidoria.dto;

import erp.uniaura.modules.ouvidoria.model.StatusManifestacao;
import erp.uniaura.modules.ouvidoria.model.TipoManifestacao;

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
public class ManifestacaoResponseDTO {

    private UUID                                    id;
    private String                                  protocolo;
    private TipoManifestacao                        tipo;
    private String                                  assunto;
    private String                                  descricao;
    private String                                  setor;
    private StatusManifestacao                      status;
    private Boolean                                 anonima;
    private UUID                                    autorId;
    private String                                  autorNome;
    private UUID                                    responsavelId;
    private String                                  responsavelNome;
    private LocalDate                               prazoResposta;
    private String                                  respostaFinal;
    private LocalDateTime                           respondidoEm;
    private LocalDateTime                           encerradoEm;
    private LocalDateTime                           criadoEm;
    private LocalDateTime                           atualizadoEm;
    private List<RespostaManifestacaoResponseDTO>   respostas;
}
