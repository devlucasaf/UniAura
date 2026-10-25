package erp.uniaura.services.web.turma.pesquisarTurmas.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Curso;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.services.common.curso.BuscarCursoComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarTurmasServiceV1 extends BaseService {

    private final BuscarCursoComponent buscarCursoComponent;

    @Transactional(readOnly = true)
    public PesquisarTurmasRetornoV1 pesquisar(PesquisarTurmasParamsV1 params) {
        Curso curso = null;
        if (params.getCursoUid() != null) {
            curso = buscarCursoComponent.buscarPorUid(params.getCursoUid());
        }
        Curso cursoFiltrado = curso;

        PageableQuery.Result<PesquisarTurmasTurmaRetornoV1> resultado = PageableQuery.<Turma, PesquisarTurmasTurmaRetornoV1>builder()
                .clazz(Turma.class)
                .entityManager(em())
                .first(params.getDe())
                .limit(params.getQtd())
                .order(params.getOrdem())
                .predicate((criteria, from) -> {
                    List<Predicate> filtros = new ArrayList<>();

                    if (params.getCodigo() != null && !params.getCodigo().isBlank()) {
                        filtros.add(cb().equal(from.get("codigo"), params.getCodigo().trim()));
                    }

                    if (cursoFiltrado != null) {
                        filtros.add(cb().equal(from.get("curso"), cursoFiltrado));
                    }

                    if (params.getPeriodoLetivo() != null && !params.getPeriodoLetivo().isBlank()) {
                        filtros.add(cb().equal(from.get("periodoLetivo"), params.getPeriodoLetivo().trim()));
                    }

                    if (!filtros.isEmpty()) {
                        criteria.where(filtros.toArray(new Predicate[0]));
                    }
                })
                .converter(this::paraRetorno)
                .build()
                .query();

        PesquisarTurmasRetornoV1 retorno = new PesquisarTurmasRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    private PesquisarTurmasTurmaRetornoV1 paraRetorno(Turma turma) {
        Professor regente = turma.getProfessorRegente();
        PesquisarTurmasTurmaRetornoV1 item = new PesquisarTurmasTurmaRetornoV1();

        item.setUid(turma.getUid());
        item.setId(turma.getId());
        item.setCodigo(turma.getCodigo());
        item.setCursoUid(turma.getCurso().getUid());
        item.setCursoNome(turma.getCurso().getNome());
        item.setPeriodoLetivo(turma.getPeriodoLetivo());
        item.setSerie(turma.getSerie());
        item.setSala(turma.getSala());
        item.setTurno(turma.getTurno());
        item.setProfessorRegenteId(regente == null ? null : regente.getId());
        item.setProfessorRegenteNome(regente == null ? null : regente.getUsuario().getNome());
        item.setCapacidadeMaxima(turma.getCapacidadeMaxima());
        item.setAtiva(turma.getAtiva());

        return item;
    }
}
