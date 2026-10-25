package erp.uniaura.services.web.atividade.pesquisarAtividadesDaTurmaDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarAtividadesDaTurmaDisciplinaRetornoV1 {

    @Schema(description = "Total de atividades do vínculo, ignorando a paginação")
    private Long total;

    @Schema(description = "Atividades da página pedida")
    private List<PesquisarAtividadesDaTurmaDisciplinaAtividadeRetornoV1> lista;
}
