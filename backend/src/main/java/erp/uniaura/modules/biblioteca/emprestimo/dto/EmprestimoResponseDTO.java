package erp.uniaura.modules.biblioteca.emprestimo.dto;

import erp.uniaura.modules.biblioteca.emprestimo.model.StatusEmprestimo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmprestimoResponseDTO {

    private Long              id;
    private Long              exemplarId;
    private String            exemplarCodigoBarras;
    private Long              livroId;
    private String            livroTitulo;
    private Long              usuarioId;
    private String            usuarioNome;
    private LocalDateTime     dataEmprestimo;
    private LocalDateTime     dataDevolucaoPrevista;
    private LocalDateTime     dataDevolucaoEfetiva;
    private Integer           renovacoes;
    private StatusEmprestimo  status;
    private BigDecimal        valorMulta;
    private Integer           diasAtraso;
}

