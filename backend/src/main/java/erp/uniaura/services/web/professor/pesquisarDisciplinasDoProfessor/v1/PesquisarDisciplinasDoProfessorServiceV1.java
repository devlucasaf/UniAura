package erp.uniaura.services.web.professor.pesquisarDisciplinasDoProfessor.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.ProfessorDisciplina;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PesquisarDisciplinasDoProfessorServiceV1 extends BaseService {

    private final BuscarProfessorComponent buscarProfessorComponent;

    // --- LISTA AS DISCIPLINAS VINCULADAS AO PROFESSOR ---
    @Transactional(readOnly = true)
    public PesquisarDisciplinasDoProfessorRetornoV1 pesquisar(PesquisarDisciplinasDoProfessorParamsV1 params) {
        Professor professor = buscarProfessorComponent.buscarPorUid(params.getProfessorUid());

        List<ProfessorDisciplina> vinculos = em()
                .createQuery("select v from ProfessorDisciplina v where v.professor = :professor order by v.id", ProfessorDisciplina.class)
                .setParameter("professor", professor)
                .getResultList();

        Map<Long, Disciplina> disciplinasPorId = new HashMap<>();
        if (!vinculos.isEmpty()) {
            List<Long> ids = vinculos.stream().map(ProfessorDisciplina::getDisciplinaId).toList();

            em().createQuery("select d from Disciplina d where d.id in :ids", Disciplina.class)
                    .setParameter("ids", ids)
                    .getResultList()
                    .forEach(disciplina -> disciplinasPorId.put(disciplina.getId(), disciplina));
        }

        PesquisarDisciplinasDoProfessorRetornoV1 retorno = new PesquisarDisciplinasDoProfessorRetornoV1();
        retorno.setLista(vinculos.stream().map(vinculo -> paraRetorno(vinculo, disciplinasPorId.get(vinculo.getDisciplinaId()))).toList());

        return retorno;
    }

    // --- CONVERTE O VÍNCULO NO ITEM DE RETORNO DA DISCIPLINA ---
    private PesquisarDisciplinasDoProfessorDisciplinaRetornoV1 paraRetorno(ProfessorDisciplina vinculo, Disciplina disciplina) {
        PesquisarDisciplinasDoProfessorDisciplinaRetornoV1 item = new PesquisarDisciplinasDoProfessorDisciplinaRetornoV1();

        item.setUid(vinculo.getUid());
        item.setDisciplinaUid(disciplina == null ? null : disciplina.getUid());
        item.setDisciplinaCodigo(disciplina == null ? null : disciplina.getCodigo());
        item.setDisciplinaNome(disciplina == null ? null : disciplina.getNome());
        item.setCriadoEm(vinculo.getCriadoEm());

        return item;
    }
}
