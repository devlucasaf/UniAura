package erp.uniaura.services.web.biblioteca.detalharEmprestimo.v1;

import erp.uniaura.persistence.entities.StatusEmprestimo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharEmprestimoRetornoV1 {

    @Schema(description = "Identificador público do empréstimo")
    private Long                uid;
    private String              livroTitulo;
    private String              exemplarCodigoBarras;
    private String              usuarioNome;
    private LocalDateTime       dataEmprestimo;
    private LocalDateTime       dataDevolucaoPrevista;
    private LocalDateTime       dataDevolucaoEfetiva;
    private Integer             renovacoes;
    private StatusEmprestimo    status;
    private Integer             diasAtraso;

    @Schema(description = "Valor da multa gerada pelo atraso; nulo quando não houve multa")
    private BigDecimal          valorMulta;
}
