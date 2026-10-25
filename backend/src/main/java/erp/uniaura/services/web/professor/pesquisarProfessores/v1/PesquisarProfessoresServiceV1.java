package erp.uniaura.services.web.professor.pesquisarProfessores.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Professor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PesquisarProfessoresServiceV1 extends BaseService {

    // --- PESQUISA PROFESSORES COM PAGINAÇÃO E FILTRO POR ATIVO ---
    @Transactional(readOnly = true)
    public PesquisarProfessoresRetornoV1 pesquisar(PesquisarProfessoresParamsV1 params) {
        PageableQuery.Result<PesquisarProfessoresProfessorRetornoV1> resultado =
                PageableQuery.<Professor, PesquisarProfessoresProfessorRetornoV1>builder()
                        .clazz(Professor.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            if (params.getAtivo() != null) {
                                criteria.where(cb().equal(from.get("ativo"), params.getAtivo()));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarProfessoresRetornoV1 retorno = new PesquisarProfessoresRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O PROFESSOR NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarProfessoresProfessorRetornoV1 paraRetorno(Professor professor) {
        PesquisarProfessoresProfessorRetornoV1 item = new PesquisarProfessoresProfessorRetornoV1();

        item.setUid(professor.getUid());
        item.setId(professor.getId());
        item.setNome(professor.getUsuario().getNome());
        item.setEmail(professor.getUsuario().getEmail());
        item.setFormacao(professor.getFormacao());
        item.setAreaAtuacao(professor.getAreaAtuacao());
        item.setCargaHorariaSemanal(professor.getCargaHorariaSemanal());
        item.setDataAdmissao(professor.getDataAdmissao());
        item.setAtivo(professor.getAtivo());

        return item;
    }
}
