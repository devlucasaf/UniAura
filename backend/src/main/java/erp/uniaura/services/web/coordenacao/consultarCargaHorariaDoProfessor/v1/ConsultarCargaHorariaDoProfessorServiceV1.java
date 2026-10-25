package erp.uniaura.services.web.coordenacao.consultarCargaHorariaDoProfessor.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.services.common.coordenacao.BuscarAlocacoesDoProfessorComponent;
import erp.uniaura.services.common.coordenacao.CalcularHorasSemanaisComponent;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarCargaHorariaDoProfessorServiceV1 extends BaseService {

    private static final int ESCALA = 2;

    private final BuscarProfessorComponent buscarProfessorComponent;
    private final BuscarAlocacoesDoProfessorComponent buscarAlocacoesDoProfessorComponent;
    private final CalcularHorasSemanaisComponent calcularHorasSemanaisComponent;

    // --- CONSULTA A CARGA HORÁRIA ALOCADA E AS AULAS DO PROFESSOR ---
    @Transactional(readOnly = true)
    public ConsultarCargaHorariaDoProfessorRetornoV1 consultar(ConsultarCargaHorariaDoProfessorParamsV1 params) {
        Professor professor = buscarProfessorComponent.buscarPorUid(params.getProfessorUid());

        String periodo = null;

        if (params.getPeriodoLetivo() != null && !params.getPeriodoLetivo().isBlank()) {
            periodo = params.getPeriodoLetivo();
        }

        List<TurmaDisciplina> aulas = buscarAlocacoesDoProfessorComponent.buscar(professor, periodo);
        BigDecimal alocadas = calcularHorasSemanaisComponent.somar(aulas);

        ConsultarCargaHorariaDoProfessorRetornoV1 retorno = new ConsultarCargaHorariaDoProfessorRetornoV1();

        retorno.setProfessorUid(professor.getUid());
        retorno.setPeriodoLetivo(periodo);
        retorno.setHorasSemanaisAlocadas(alocadas);
        retorno.setCargaHorariaSemanalContratada(professor.getCargaHorariaSemanal());

        if (professor.getUsuario() != null) {
            retorno.setProfessorNome(professor.getUsuario().getNome());
        }

        if (professor.getCargaHorariaSemanal() != null) {
            retorno.setHorasSemanaisDisponiveis(BigDecimal.valueOf(professor.getCargaHorariaSemanal())
                    .subtract(alocadas)
                    .setScale(ESCALA, RoundingMode.HALF_UP));
        }

        List<ConsultarCargaHorariaDoProfessorAlocacaoRetornoV1> alocacoes = new ArrayList<>();

        for (TurmaDisciplina aula : aulas) {
            ConsultarCargaHorariaDoProfessorAlocacaoRetornoV1 item = new ConsultarCargaHorariaDoProfessorAlocacaoRetornoV1();

            item.setTurmaDisciplinaUid(aula.getUid());
            item.setTurmaUid(aula.getTurma().getUid());
            item.setTurmaCodigo(aula.getTurma().getCodigo());
            item.setPeriodoLetivo(aula.getTurma().getPeriodoLetivo());
            item.setDisciplinaUid(aula.getDisciplina().getUid());
            item.setDisciplinaCodigo(aula.getDisciplina().getCodigo());
            item.setDisciplinaNome(aula.getDisciplina().getNome());
            item.setDiaSemana(aula.getDiaSemana());
            item.setHorarioInicio(aula.getHorarioInicio());
            item.setHorarioFim(aula.getHorarioFim());

            alocacoes.add(item);
        }

        retorno.setAlocacoes(alocacoes);

        return retorno;
    }
}
