package erp.uniaura.modules.comunicado.dto;

import erp.uniaura.modules.comunicado.model.PublicoAlvoComunicado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComunicadoResponseDTO {

    private Long id;
    private String titulo;
    private String mensagem;
    private PublicoAlvoComunicado publicoAlvo;
    private Long turmaId;
    private Boolean importante;
    private Long autorId;
    private String autorNome;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
