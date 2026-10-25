package erp.uniaura.services.web.turma.pesquisarDisciplinasDaTurma.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarDisciplinasDaTurmaServiceV1 extends BaseService {

    private final BuscarTurmaComponent buscarTurmaComponent;

    @Transactional(readOnly = true)
    public PesquisarDisciplinasDaTurmaRetornoV1 pesquisar(PesquisarDisciplinasDaTurmaParamsV1 params) {
        Turma turma = buscarTurmaComponent.buscarPorUid(params.getTurmaUid());

        List<TurmaDisciplina> vinculos = em()
                .createQuery("select v from TurmaDisciplina v where v.turma = :turma order by v.diaSemana, v.horarioInicio", TurmaDisciplina.class)
                .setParameter("turma", turma)
                .getResultList();

        PesquisarDisciplinasDaTurmaRetornoV1 retorno = new PesquisarDisciplinasDaTurmaRetornoV1();
        retorno.setLista(vinculos.stream().map(this::paraRetorno).toList());

        return retorno;
    }

    private PesquisarDisciplinasDaTurmaDisciplinaRetornoV1 paraRetorno(TurmaDisciplina vinculo) {
        PesquisarDisciplinasDaTurmaDisciplinaRetornoV1 item = new PesquisarDisciplinasDaTurmaDisciplinaRetornoV1();

        item.setUid(vinculo.getUid());
        item.setId(vinculo.getId());
        item.setDisciplinaUid(vinculo.getDisciplina().getUid());
        item.setDisciplinaCodigo(vinculo.getDisciplina().getCodigo());
        item.setDisciplinaNome(vinculo.getDisciplina().getNome());
        item.setProfessorId(vinculo.getProfessor().getId());
        item.setProfessorNome(vinculo.getProfessor().getUsuario().getNome());
        item.setDiaSemana(vinculo.getDiaSemana());
        item.setHorarioInicio(vinculo.getHorarioInicio());
        item.setHorarioFim(vinculo.getHorarioFim());
        item.setCriadoEm(vinculo.getCriadoEm());

        return item;
    }
}
