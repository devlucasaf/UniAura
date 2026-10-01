package erp.uniaura.modules.documento.dto;

import erp.uniaura.modules.documento.model.StatusDocumento;
import erp.uniaura.modules.documento.model.TipoDocumento;

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
public class DocumentoAlunoResponseDTO {

    private Long            id;
    private Long            alunoId;
    private String          alunoNome;
    private TipoDocumento   tipo;
    private String          nomeArquivo;
    private String          arquivoUrl;
    private StatusDocumento status;
    private Integer         versao;
    private String          observacoes;
    private String          analisadoPorNome;
    private LocalDateTime   analisadoEm;
    private LocalDateTime   enviadoEm;
}
