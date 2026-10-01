package erp.uniaura.modules.material.dto;

import erp.uniaura.modules.material.model.TipoMaterial;

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
public class MaterialResponseDTO {

    private Long            id;
    private Long            turmaDisciplinaId;
    private String          titulo;
    private String          descricao;
    private TipoMaterial    tipo;
    private String          arquivoUrl;
    private String          linkUrl;
    private Long            professorId;
    private String          professorNome;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}

