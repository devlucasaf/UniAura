package erp.uniaura.services.web.nota.pesquisarNotasDaTurmaDisciplina.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.persistence.entities.Nota;
import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.services.common.disciplina.BuscarDisciplinaComponent;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarNotasDaTurmaDisciplinaServiceV1 extends BaseService {

    private final BuscarTurmaComponent buscarTurmaComponent;
    private final BuscarDisciplinaComponent buscarDisciplinaComponent;

    // --- PESQUISA AS NOTAS DA DISCIPLINA NA TURMA COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarNotasDaTurmaDisciplinaRetornoV1 pesquisar(PesquisarNotasDaTurmaDisciplinaParamsV1 params) {
        Turma turma = buscarTurmaComponent.buscarPorUid(params.getTurmaUid());
        Disciplina disciplina = buscarDisciplinaComponent.buscarPorUid(params.getDisciplinaUid());

        PageableQuery.Result<PesquisarNotasDaTurmaDisciplinaNotaRetornoV1> resultado =
                PageableQuery.<Nota, PesquisarNotasDaTurmaDisciplinaNotaRetornoV1>builder()
                        .clazz(Nota.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            Predicate daTurma = cb().equal(from.get("turmaDisciplina").get("turma"), turma);
                            Predicate daDisciplina = cb().equal(from.get("turmaDisciplina").get("disciplina"), disciplina);
                            criteria.where(daTurma, daDisciplina);
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarNotasDaTurmaDisciplinaRetornoV1 retorno = new PesquisarNotasDaTurmaDisciplinaRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A NOTA NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarNotasDaTurmaDisciplinaNotaRetornoV1 paraRetorno(Nota nota) {
        PesquisarNotasDaTurmaDisciplinaNotaRetornoV1 item = new PesquisarNotasDaTurmaDisciplinaNotaRetornoV1();

        item.setUid(nota.getUid());
        item.setAlunoUid(nota.getAluno().getUid());
        item.setAlunoNome(nota.getAluno().getUsuario().getNome());
        item.setAlunoMatriculaRA(nota.getAluno().getMatriculaRA());
        item.setPeriodoAvaliacao(nota.getPeriodoAvaliacao());
        item.setTipoAvaliacao(nota.getTipoAvaliacao());
        item.setValor(nota.getValor());
        item.setPeso(nota.getPeso());
        item.setObservacoes(nota.getObservacoes());

        if (nota.getLancadaPor() != null) {
            item.setLancadaPorNome(nota.getLancadaPor().getNome());
        }

        item.setLancadaEm(nota.getLancadaEm());

        return item;
    }
}
