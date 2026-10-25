package erp.uniaura.services.web.estagio.pesquisarEstagios.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Estagio;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarEstagiosServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- PESQUISA ESTÁGIOS COM PAGINAÇÃO E FILTROS POR ALUNO E STATUS ---
    @Transactional(readOnly = true)
    public PesquisarEstagiosRetornoV1 pesquisar(PesquisarEstagiosParamsV1 params) {
        Aluno aluno = null;

        if (params.getAlunoUid() != null) {
            aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());
        }
        final Aluno alunoFiltrado = aluno;

        PageableQuery.Result<PesquisarEstagiosEstagioRetornoV1> resultado =
                PageableQuery.<Estagio, PesquisarEstagiosEstagioRetornoV1>builder()
                        .clazz(Estagio.class)
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

        PesquisarEstagiosRetornoV1 retorno = new PesquisarEstagiosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O ESTÁGIO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarEstagiosEstagioRetornoV1 paraRetorno(Estagio estagio) {
        PesquisarEstagiosEstagioRetornoV1 item = new PesquisarEstagiosEstagioRetornoV1();

        item.setUid(estagio.getUid());
        item.setAlunoUid(estagio.getAluno().getUid());
        item.setAlunoNome(estagio.getAluno().getUsuario().getNome());
        item.setEmpresaConcedente(estagio.getEmpresaConcedente());

        if (estagio.getProfessorOrientador() != null) {
            item.setProfessorOrientadorNome(estagio.getProfessorOrientador().getUsuario().getNome());
        }

        item.setDataInicio(estagio.getDataInicio());
        item.setDataFimPrevista(estagio.getDataFimPrevista());
        item.setCargaHorariaTotal(estagio.getCargaHorariaTotal());
        item.setCargaHorariaCumprida(estagio.getCargaHorariaCumprida());
        item.setStatus(estagio.getStatus());

        return item;
    }
}
