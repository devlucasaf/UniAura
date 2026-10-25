package erp.uniaura.services.web.tcc.pesquisarTccs.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarTccsServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarProfessorComponent buscarProfessorComponent;

    @Transactional(readOnly = true)
    public PesquisarTccsRetornoV1 pesquisar(PesquisarTccsParamsV1 params) {
        Aluno aluno = null;
        if (params.getAlunoUid() != null) {
            aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());
        }

        final Aluno alunoFiltrado = aluno;

        Professor orientador = null;
        if (params.getProfessorOrientadorUid() != null) {
            orientador = buscarProfessorComponent.buscarPorUid(params.getProfessorOrientadorUid());
        }
        final Professor orientadorFiltrado = orientador;

        PageableQuery.Result<PesquisarTccsTccRetornoV1> resultado =
                PageableQuery.<Tcc, PesquisarTccsTccRetornoV1>builder()
                        .clazz(Tcc.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            if (alunoFiltrado != null) {
                                condicoes.add(cb().equal(from.get("aluno"), alunoFiltrado));
                            }

                            if (orientadorFiltrado != null) {
                                condicoes.add(cb().equal(from.get("professorOrientador"), orientadorFiltrado));
                            }

                            if (params.getStatus() != null) {
                                condicoes.add(cb().equal(from.get("status"), params.getStatus()));
                            }

                            if (!condicoes.isEmpty()) {
                                criteria.where(condicoes.toArray(new Predicate[0]));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarTccsRetornoV1 retorno = new PesquisarTccsRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    private PesquisarTccsTccRetornoV1 paraRetorno(Tcc tcc) {
        PesquisarTccsTccRetornoV1 item = new PesquisarTccsTccRetornoV1();

        item.setUid(tcc.getUid());
        item.setAlunoUid(tcc.getAluno().getUid());
        item.setAlunoNome(tcc.getAluno().getUsuario().getNome());
        item.setTitulo(tcc.getTitulo());
        item.setProfessorOrientadorNome(tcc.getProfessorOrientador().getUsuario().getNome());
        item.setPeriodoLetivo(tcc.getPeriodoLetivo());
        item.setStatus(tcc.getStatus());
        item.setNotaFinal(tcc.getNotaFinal());
        item.setDataDefesa(tcc.getDataDefesa());

        return item;
    }
}
