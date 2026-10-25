package erp.uniaura.services.web.biblioteca.pesquisarEmprestimosDoUsuario.v1;

import erp.uniaura.persistence.entities.StatusEmprestimo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarEmprestimosDoUsuarioEmprestimoRetornoV1 {

    @Schema(description = "Identificador público do empréstimo")
    private Long                uid;
    private String              livroTitulo;
    private String              exemplarCodigoBarras;
    private LocalDateTime       dataEmprestimo;
    private LocalDateTime       dataDevolucaoPrevista;
    private LocalDateTime       dataDevolucaoEfetiva;
    private Integer             renovacoes;
    private StatusEmprestimo    status;
    private Integer             diasAtraso;
    private BigDecimal          valorMulta;
}
