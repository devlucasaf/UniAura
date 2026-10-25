package erp.uniaura.services.web.coordenacao.alocarProfessorNaTurmaDisciplina.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.services.common.coordenacao.BuscarAlocacoesDoProfessorComponent;
import erp.uniaura.services.common.coordenacao.CalcularHorasSemanaisComponent;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;
import erp.uniaura.services.common.professor.VerificarProfessorHabilitadoComponent;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlocarProfessorNaTurmaDisciplinaServiceV1 extends BaseService {

    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;
    private final BuscarProfessorComponent buscarProfessorComponent;
    private final VerificarProfessorHabilitadoComponent verificarProfessorHabilitadoComponent;
    private final BuscarAlocacoesDoProfessorComponent buscarAlocacoesDoProfessorComponent;
    private final CalcularHorasSemanaisComponent calcularHorasSemanaisComponent;

    // --- ALOCA O PROFESSOR NA TURMA/DISCIPLINA APÓS VALIDAR HABILITAÇÃO, HORÁRIO E CARGA HORÁRIA ---
    @Transactional
    public void alocar(AlocarProfessorNaTurmaDisciplinaParamsV1 params) {
        TurmaDisciplina vinculo = buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid());
        Professor professor = buscarProfessorComponent.buscarPorUid(params.getProfessorUid());

        if (vinculo.getProfessor().getId().equals(professor.getId())) {
            throw new BusinessException("O professor informado já é o responsável por esta turma/disciplina.");
        }

        validarHabilitacao(professor, vinculo);
        validarConflitoDeHorario(professor, vinculo);
        validarCargaHoraria(professor, vinculo);

        vinculo.setProfessor(professor);
        vinculo.merge();
    }

    // --- O PROFESSOR PRECISA ESTAR HABILITADO A LECIONAR A DISCIPLINA ---
    private void validarHabilitacao(Professor professor, TurmaDisciplina vinculo) {
        if (!verificarProfessorHabilitadoComponent.habilitado(professor, vinculo.getDisciplina().getId())) {
            throw new BusinessException("O professor selecionado não está habilitado a lecionar a disciplina informada.");
        }
    }

    // --- IMPEDE QUE O PROFESSOR FIQUE COM DUAS AULAS SOBREPOSTAS NO MESMO PERÍODO LETIVO ---
    private void validarConflitoDeHorario(Professor professor, TurmaDisciplina vinculo) {
        List<TurmaDisciplina> conflitos = buscarAlocacoesDoProfessorComponent.buscarConflitosDeHorario(professor, vinculo);

        if (conflitos.isEmpty()) {
            return;
        }

        TurmaDisciplina conflito = conflitos.get(0);

        throw new BusinessException(
                "Conflito de horário: o professor já leciona %s na turma %s em %s das %s às %s."
                        .formatted(conflito.getDisciplina().getNome(),
                                conflito.getTurma().getCodigo(),
                                conflito.getDiaSemana(),
                                conflito.getHorarioInicio(),
                                conflito.getHorarioFim()));
    }

    // --- IMPEDE QUE A ALOCAÇÃO ULTRAPASSE A CARGA HORÁRIA SEMANAL CONTRATADA ---
    private void validarCargaHoraria(Professor professor, TurmaDisciplina vinculo) {
        Integer contratada = professor.getCargaHorariaSemanal();

        if (contratada == null) {
            return;
        }

        List<TurmaDisciplina> atuais = buscarAlocacoesDoProfessorComponent.buscar(professor, vinculo.getTurma().getPeriodoLetivo());

        BigDecimal jaAlocadas = calcularHorasSemanaisComponent.somar(atuais);
        BigDecimal novaAula = calcularHorasSemanaisComponent.horasDaAula(vinculo);

        if (jaAlocadas.add(novaAula).compareTo(BigDecimal.valueOf(contratada)) > 0) {
            throw new BusinessException(
                    "A alocação ultrapassa a carga horária semanal do professor: %s h já alocadas + %s h desta aula excedem as %d h contratadas."
                            .formatted(jaAlocadas, novaAula, contratada));
        }
    }
}
