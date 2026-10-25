package erp.uniaura.services.web.frequencia.consultarResumoDeFrequenciaDoAluno.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.persistence.entities.Frequencia;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.frequencia.CalcularPercentualDePresencaComponent;
import erp.uniaura.services.common.frequencia.PercentualDePresenca;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConsultarResumoDeFrequenciaDoAlunoServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final CalcularPercentualDePresencaComponent calcularPercentualDePresencaComponent;

    // --- RESUMO DE FREQUÊNCIA DO ALUNO AGRUPADO POR DISCIPLINA ---
    @Transactional(readOnly = true)
    public ConsultarResumoDeFrequenciaDoAlunoRetornoV1 consultar(ConsultarResumoDeFrequenciaDoAlunoParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());

        List<Frequencia> frequencias = em()
                .createQuery("select f from Frequencia f where f.aluno = :aluno", Frequencia.class)
                .setParameter("aluno", aluno)
                .getResultList();

        if (frequencias.isEmpty()) {
            throw new BusinessException("O aluno não possui frequência registrada.");
        }

        // --- AGRUPA POR DISCIPLINA, NA ORDEM EM QUE APARECEM ---
        Map<Disciplina, List<Frequencia>> porDisciplina = new LinkedHashMap<>();
        for (Frequencia frequencia : frequencias) {
            Disciplina disciplina = frequencia.getAula().getTurmaDisciplina().getDisciplina();
            porDisciplina.computeIfAbsent(disciplina, chave -> new ArrayList<>()).add(frequencia);
        }

        List<ConsultarResumoDeFrequenciaDoAlunoDisciplinaRetornoV1> linhas = new ArrayList<>();

        for (Map.Entry<Disciplina, List<Frequencia>> grupo : porDisciplina.entrySet()) {
            PercentualDePresenca presenca = calcularPercentualDePresencaComponent.calcular(grupo.getValue());

            ConsultarResumoDeFrequenciaDoAlunoDisciplinaRetornoV1 linha = new ConsultarResumoDeFrequenciaDoAlunoDisciplinaRetornoV1();

            linha.setDisciplinaUid(grupo.getKey().getUid());
            linha.setDisciplinaNome(grupo.getKey().getNome());
            linha.setTotalAulas(presenca.totalAulas());
            linha.setPresencas(presenca.presencas());
            linha.setFaltas(presenca.faltas());
            linha.setPercentual(presenca.percentual());

            linhas.add(linha);
        }

        ConsultarResumoDeFrequenciaDoAlunoRetornoV1 retorno = new ConsultarResumoDeFrequenciaDoAlunoRetornoV1();

        retorno.setAlunoNome(aluno.getUsuario().getNome());
        retorno.setAlunoMatriculaRA(aluno.getMatriculaRA());
        retorno.setDisciplinas(linhas);

        return retorno;
    }
}
