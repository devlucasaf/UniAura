package erp.uniaura.services.web.nota.pesquisarNotasDoAluno.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Nota;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarNotasDoAlunoServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- PESQUISA AS NOTAS DE UM ALUNO COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarNotasDoAlunoRetornoV1 pesquisar(PesquisarNotasDoAlunoParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());

        PageableQuery.Result<PesquisarNotasDoAlunoNotaRetornoV1> resultado =
                PageableQuery.<Nota, PesquisarNotasDoAlunoNotaRetornoV1>builder()
                        .clazz(Nota.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("aluno"), aluno)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarNotasDoAlunoRetornoV1 retorno = new PesquisarNotasDoAlunoRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A NOTA NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarNotasDoAlunoNotaRetornoV1 paraRetorno(Nota nota) {
        PesquisarNotasDoAlunoNotaRetornoV1 item = new PesquisarNotasDoAlunoNotaRetornoV1();

        item.setUid(nota.getUid());
        item.setTurmaCodigo(nota.getTurmaDisciplina().getTurma().getCodigo());
        item.setDisciplinaNome(nota.getTurmaDisciplina().getDisciplina().getNome());
        item.setProfessorNome(nota.getTurmaDisciplina().getProfessor().getUsuario().getNome());
        item.setPeriodoAvaliacao(nota.getPeriodoAvaliacao());
        item.setTipoAvaliacao(nota.getTipoAvaliacao());
        item.setValor(nota.getValor());
        item.setPeso(nota.getPeso());
        item.setObservacoes(nota.getObservacoes());
        item.setLancadaEm(nota.getLancadaEm());

        return item;
    }
}
