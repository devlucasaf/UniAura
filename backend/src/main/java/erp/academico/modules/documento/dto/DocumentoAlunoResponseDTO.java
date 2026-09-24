package erp.academico.modules.documento.dto;

import erp.academico.modules.documento.model.StatusDocumento;
import erp.academico.modules.documento.model.TipoDocumento;

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
public class DocumentoAlunoResponseDTO {

    private UUID id;
    private UUID alunoId;
    private String alunoNome;
    private TipoDocumento tipo;
    private String nomeArquivo;
    private String arquivoUrl;
    private StatusDocumento status;
    private String observacoes;
    private String analisadoPorNome;
    private LocalDateTime analisadoEm;
    private LocalDateTime enviadoEm;
}
