package erp.uniaura.modules.lgpd.dto;

import erp.uniaura.modules.aluno.dto.AlunoResponseDTO;
import erp.uniaura.modules.documento.dto.DocumentoAlunoResponseDTO;
import erp.uniaura.modules.financeiro.dto.MensalidadeResponseDTO;
import erp.uniaura.modules.nota.dto.NotaResponseDTO;
import erp.uniaura.modules.usuario.dto.UsuarioResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DadosPessoaisResponseDTO {

    private UsuarioResponseDTO              usuario;
    private AlunoResponseDTO                aluno;
    private List<MensalidadeResponseDTO>    mensalidades;
    private List<NotaResponseDTO>           notas;
    private List<DocumentoAlunoResponseDTO> documentos;
    private LocalDateTime                   exportadoEm;
}
