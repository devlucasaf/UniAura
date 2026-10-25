package erp.uniaura.services.web.frequencia.consultarPercentualDePresenca.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.persistence.entities.Frequencia;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.disciplina.BuscarDisciplinaComponent;
import erp.uniaura.services.common.frequencia.CalcularPercentualDePresencaComponent;
import erp.uniaura.services.common.frequencia.PercentualDePresenca;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarPercentualDePresencaServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarDisciplinaComponent buscarDisciplinaComponent;
    private final CalcularPercentualDePresencaComponent calcularPercentualDePresencaComponent;

    // --- CONSULTA O PERCENTUAL DE PRESENÇA DO ALUNO NA DISCIPLINA ---
    @Transactional(readOnly = true)
    public ConsultarPercentualDePresencaRetornoV1 consultar(ConsultarPercentualDePresencaParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());
        Disciplina disciplina = buscarDisciplinaComponent.buscarPorUid(params.getDisciplinaUid());

        List<Frequencia> frequencias = em()
                .createQuery("select f from Frequencia f where f.aluno = :aluno and f.aula.turmaDisciplina.disciplina = :disciplina", Frequencia.class)
                .setParameter("aluno", aluno)
                .setParameter("disciplina", disciplina)
                .getResultList();

        if (frequencias.isEmpty()) {
            throw new BusinessException("O aluno não possui frequência registrada nesta disciplina.");
        }

        PercentualDePresenca presenca = calcularPercentualDePresencaComponent.calcular(frequencias);

        ConsultarPercentualDePresencaRetornoV1 retorno = new ConsultarPercentualDePresencaRetornoV1();

        retorno.setAlunoNome(aluno.getUsuario().getNome());
        retorno.setDisciplinaNome(disciplina.getNome());
        retorno.setTotalAulas(presenca.totalAulas());
        retorno.setPresencas(presenca.presencas());
        retorno.setFaltas(presenca.faltas());
        retorno.setPercentual(presenca.percentual());

        return retorno;
    }
}
