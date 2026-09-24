package erp.academico.modules.comunicado.dto;

import erp.academico.modules.comunicado.model.PublicoAlvoComunicado;

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
public class ComunicadoResponseDTO {

    private UUID id;
    private String titulo;
    private String mensagem;
    private PublicoAlvoComunicado publicoAlvo;
    private UUID turmaId;
    private Boolean importante;
    private UUID autorId;
    private String autorNome;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
