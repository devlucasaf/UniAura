package erp.uniaura.services.web.matricula.pesquisarMatriculas.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Matricula;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarMatriculasServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- PESQUISA MATRÍCULAS COM PAGINAÇÃO E FILTROS POR ALUNO E STATUS ---
    @Transactional(readOnly = true)
    public PesquisarMatriculasRetornoV1 pesquisar(PesquisarMatriculasParamsV1 params) {
        Aluno aluno = null;

        if (params.getAlunoUid() != null) {
            aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());
        }
        final Aluno alunoFiltrado = aluno;

        PageableQuery.Result<PesquisarMatriculasMatriculaRetornoV1> resultado =
                PageableQuery.<Matricula, PesquisarMatriculasMatriculaRetornoV1>builder()
                        .clazz(Matricula.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            if (alunoFiltrado != null) {
                                condicoes.add(cb().equal(from.get("aluno"), alunoFiltrado));
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

        PesquisarMatriculasRetornoV1 retorno = new PesquisarMatriculasRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A MATRÍCULA NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarMatriculasMatriculaRetornoV1 paraRetorno(Matricula matricula) {
        PesquisarMatriculasMatriculaRetornoV1 item = new PesquisarMatriculasMatriculaRetornoV1();

        item.setUid(matricula.getUid());
        item.setAlunoUid(matricula.getAluno().getUid());
        item.setAlunoNome(matricula.getAluno().getUsuario().getNome());
        item.setAlunoMatriculaRA(matricula.getAluno().getMatriculaRA());
        item.setTurmaUid(matricula.getTurma().getUid());
        item.setTurmaCodigo(matricula.getTurma().getCodigo());
        item.setTurmaPeriodoLetivo(matricula.getTurma().getPeriodoLetivo());
        item.setDataMatricula(matricula.getDataMatricula());
        item.setStatus(matricula.getStatus());

        return item;
    }
}
