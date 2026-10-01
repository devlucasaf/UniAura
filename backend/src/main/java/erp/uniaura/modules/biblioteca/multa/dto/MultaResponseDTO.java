package erp.uniaura.modules.biblioteca.multa.dto;

import erp.uniaura.modules.biblioteca.multa.model.StatusMulta;

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
public class MultaResponseDTO {

    private Long            id;
    private Long            emprestimoId;
    private Long            usuarioId;
    private String          usuarioNome;
    private String          livroTitulo;
    private BigDecimal      valor;
    private Integer         diasAtraso;
    private StatusMulta     status;
    private LocalDateTime   geradaEm;
    private LocalDateTime   pagaEm;
}

