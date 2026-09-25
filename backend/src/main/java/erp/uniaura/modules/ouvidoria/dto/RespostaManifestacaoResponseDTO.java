package erp.uniaura.modules.ouvidoria.dto;

import erp.uniaura.modules.ouvidoria.model.StatusManifestacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespostaManifestacaoResponseDTO {

    private UUID                id;
    private String              autorNome;
    private StatusManifestacao  statusAnterior;
    private StatusManifestacao  statusNovo;
    private String              mensagem;
    private Boolean             visivelParaAutor;
    private LocalDateTime       criadoEm;
}
