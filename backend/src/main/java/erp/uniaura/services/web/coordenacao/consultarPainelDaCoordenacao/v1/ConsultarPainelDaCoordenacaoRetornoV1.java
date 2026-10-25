package erp.uniaura.services.web.coordenacao.consultarPainelDaCoordenacao.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ConsultarPainelDaCoordenacaoRetornoV1 {

    @Schema(description = "Identificador público do curso filtrado; vazio quando consolida todos")
    private Long       cursoUid;
    private String     cursoNome;
    private String     periodoLetivo;

    @Schema(description = "Quantidade de alunos distintos por status de matrícula")
    private Map<String, Long> alunosPorStatusMatricula;
    private Long       totalAlunosAtivos;
    private Long       totalTurmas;
    private Long       turmasAtivas;
    private Long       turmasSemProfessorRegente;

    @Schema(description = "Percentual de vagas ocupadas nas turmas ativas")
    private BigDecimal ocupacaoMediaPercentual;

    @Schema(description = "Quantidade de médias apuradas")
    private Long       totalResultadosAvaliados;
    private Long       totalAprovacoes;
    private Long       totalReprovacoes;
    private BigDecimal taxaAprovacaoPercentual;
    private BigDecimal mediaGeral;
    private BigDecimal frequenciaMediaPercentual;

    @Schema(description = "Processos abertos, em análise ou aguardando o aluno (contagem global)")
    private Long       processosPendentes;

    @Schema(description = "Manifestações abertas ou em apuração (contagem global)")
    private Long       manifestacoesPendentes;

    @Schema(description = "Turmas ativas que ainda não têm professor regente")
    private List<ConsultarPainelDaCoordenacaoTurmaSemRegenteRetornoV1> turmasQueExigemAtencao;
}
