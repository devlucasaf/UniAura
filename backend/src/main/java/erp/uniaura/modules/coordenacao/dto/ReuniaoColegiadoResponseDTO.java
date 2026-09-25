package erp.uniaura.modules.coordenacao.dto;

import erp.uniaura.modules.coordenacao.model.StatusReuniao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReuniaoColegiadoResponseDTO {

    private UUID                                id;
    private UUID                                cursoId;
    private String                              cursoNome;
    private String                              titulo;
    private LocalDateTime                       dataHora;
    private String                              local;
    private String                              pauta;
    private StatusReuniao                       status;
    private String                              deliberacoes;
    private String                              motivoCancelamento;
    private LocalDateTime                       encerradaEm;
    private String                              criadaPorNome;
    private LocalDateTime                       criadoEm;
    private LocalDateTime                       atualizadoEm;

    private List<ParticipanteReuniaoResponseDTO> participantes;
}
