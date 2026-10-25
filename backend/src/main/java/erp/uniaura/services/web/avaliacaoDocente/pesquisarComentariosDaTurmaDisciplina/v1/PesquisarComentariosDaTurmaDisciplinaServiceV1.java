package erp.uniaura.services.web.avaliacaoDocente.pesquisarComentariosDaTurmaDisciplina.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.AvaliacaoDocente;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class PesquisarComentariosDaTurmaDisciplinaServiceV1 extends BaseService {

    private static final int ESCALA = 2;

    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;

    // --- PESQUISA COMENTÁRIOS DA TURMA/DISCIPLINA COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarComentariosDaTurmaDisciplinaRetornoV1 pesquisar(PesquisarComentariosDaTurmaDisciplinaParamsV1 params) {
        TurmaDisciplina turmaDisciplina = buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid());

        PageableQuery.Result<PesquisarComentariosDaTurmaDisciplinaComentarioRetornoV1> resultado =
                PageableQuery.<AvaliacaoDocente, PesquisarComentariosDaTurmaDisciplinaComentarioRetornoV1>builder()
                        .clazz(AvaliacaoDocente.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            Predicate daTurmaDisciplina = cb().equal(from.get("turmaDisciplina"), turmaDisciplina);
                            Predicate comComentario = cb().greaterThan(cb().length(cb().trim(from.<String>get("comentario"))), 0);
                            criteria.where(daTurmaDisciplina, comComentario);
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarComentariosDaTurmaDisciplinaRetornoV1 retorno = new PesquisarComentariosDaTurmaDisciplinaRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A AVALIAÇÃO NO ITEM DE COMENTÁRIO DO RETORNO ---
    private PesquisarComentariosDaTurmaDisciplinaComentarioRetornoV1 paraRetorno(AvaliacaoDocente avaliacao) {
        PesquisarComentariosDaTurmaDisciplinaComentarioRetornoV1 item = new PesquisarComentariosDaTurmaDisciplinaComentarioRetornoV1();

        BigDecimal soma = BigDecimal.valueOf(avaliacao.getNotaDidatica() + avaliacao.getNotaPontualidade() + avaliacao.getNotaDisponibilidade());

        item.setNotaGeral(soma.divide(BigDecimal.valueOf(3), ESCALA, RoundingMode.HALF_UP));
        item.setComentario(avaliacao.getComentario());
        item.setCriadoEm(avaliacao.getCriadoEm());

        return item;
    }
}
