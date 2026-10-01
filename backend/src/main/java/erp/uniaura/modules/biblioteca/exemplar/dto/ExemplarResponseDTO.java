package erp.uniaura.modules.biblioteca.exemplar.dto;

import erp.uniaura.modules.biblioteca.exemplar.model.StatusExemplar;

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
public class ExemplarResponseDTO {

    private Long            id;
    private Long            livroId;
    private String          livroTitulo;
    private String          codigoBarras;
    private String          localizacao;
    private StatusExemplar  status;
    private LocalDateTime   criadoEm;
}

