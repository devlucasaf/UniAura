package erp.uniaura.services.web.frequencia.pesquisarFrequenciasDaAula.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aula;
import erp.uniaura.persistence.entities.Frequencia;
import erp.uniaura.services.common.frequencia.BuscarAulaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarFrequenciasDaAulaServiceV1 extends BaseService {

    private final BuscarAulaComponent buscarAulaComponent;

    // --- PESQUISA AS FREQUÊNCIAS DE UMA AULA COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarFrequenciasDaAulaRetornoV1 pesquisar(PesquisarFrequenciasDaAulaParamsV1 params) {
        Aula aula = buscarAulaComponent.buscarPorUid(params.getAulaUid());

        PageableQuery.Result<PesquisarFrequenciasDaAulaFrequenciaRetornoV1> resultado =
                PageableQuery.<Frequencia, PesquisarFrequenciasDaAulaFrequenciaRetornoV1>builder()
                        .clazz(Frequencia.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("aula"), aula)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarFrequenciasDaAulaRetornoV1 retorno = new PesquisarFrequenciasDaAulaRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A FREQUÊNCIA NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarFrequenciasDaAulaFrequenciaRetornoV1 paraRetorno(Frequencia frequencia) {
        PesquisarFrequenciasDaAulaFrequenciaRetornoV1 item = new PesquisarFrequenciasDaAulaFrequenciaRetornoV1();

        item.setUid(frequencia.getUid());
        item.setAlunoUid(frequencia.getAluno().getUid());
        item.setAlunoNome(frequencia.getAluno().getUsuario().getNome());
        item.setAlunoMatriculaRA(frequencia.getAluno().getMatriculaRA());
        item.setPresente(frequencia.getPresente());
        item.setJustificativa(frequencia.getJustificativa());

        return item;
    }
}
