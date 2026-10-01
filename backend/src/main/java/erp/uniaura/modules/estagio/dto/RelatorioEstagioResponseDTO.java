package erp.uniaura.modules.estagio.dto;

import erp.uniaura.modules.estagio.model.StatusRelatorioEstagio;

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
public class RelatorioEstagioResponseDTO {

    private Long                       id;
    private Long                       estagioId;
    private String                     periodoReferencia;
    private Integer                    horasRegistradas;
    private String                     descricaoAtividades;
    private String                     comprovanteUrl;
    private StatusRelatorioEstagio     status;
    private String                     analisadoPorNome;
    private String                     observacoes;
    private LocalDateTime              analisadoEm;
    private LocalDateTime              enviadoEm;
}
