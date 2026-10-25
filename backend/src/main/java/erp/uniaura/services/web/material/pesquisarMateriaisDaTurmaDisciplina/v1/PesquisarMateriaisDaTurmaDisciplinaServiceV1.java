package erp.uniaura.services.web.material.pesquisarMateriaisDaTurmaDisciplina.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Material;

import jakarta.persistence.criteria.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PesquisarMateriaisDaTurmaDisciplinaServiceV1 extends BaseService {

    // --- PESQUISA OS MATERIAIS DA TURMA/DISCIPLINA COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarMateriaisDaTurmaDisciplinaRetornoV1 pesquisar(PesquisarMateriaisDaTurmaDisciplinaParamsV1 params) {
        PageableQuery.Result<PesquisarMateriaisDaTurmaDisciplinaMaterialRetornoV1> resultado =
                PageableQuery.<Material, PesquisarMateriaisDaTurmaDisciplinaMaterialRetornoV1>builder()
                        .clazz(Material.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            Predicate daTurma = cb().equal(from.get("turmaDisciplina").get("turma").get("uid"), params.getTurmaUid());
                            Predicate daDisciplina = cb().equal(from.get("turmaDisciplina").get("disciplina").get("uid"), params.getDisciplinaUid());
                            criteria.where(daTurma, daDisciplina);
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarMateriaisDaTurmaDisciplinaRetornoV1 retorno = new PesquisarMateriaisDaTurmaDisciplinaRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O MATERIAL NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarMateriaisDaTurmaDisciplinaMaterialRetornoV1 paraRetorno(Material material) {
        PesquisarMateriaisDaTurmaDisciplinaMaterialRetornoV1 item = new PesquisarMateriaisDaTurmaDisciplinaMaterialRetornoV1();

        item.setUid(material.getUid());
        item.setTitulo(material.getTitulo());
        item.setDescricao(material.getDescricao());
        item.setTipo(material.getTipo());
        item.setArquivoUrl(material.getArquivoUrl());
        item.setLinkUrl(material.getLinkUrl());
        item.setProfessorNome(material.getProfessor().getUsuario() == null ? null : material.getProfessor().getUsuario().getNome());
        item.setCriadoEm(material.getCriadoEm());

        return item;
    }
}
