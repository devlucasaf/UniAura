package erp.uniaura.services.web.turma.pesquisarAlunosDaTurma.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarAlunosDaTurmaServiceV1 extends BaseService {

    private final BuscarTurmaComponent buscarTurmaComponent;

    @Transactional(readOnly = true)
    public PesquisarAlunosDaTurmaRetornoV1 pesquisar(PesquisarAlunosDaTurmaParamsV1 params) {
        Turma turma = buscarTurmaComponent.buscarPorUid(params.getTurmaUid());

        PageableQuery.Result<PesquisarAlunosDaTurmaAlunoRetornoV1> resultado =
                PageableQuery.<Aluno, PesquisarAlunosDaTurmaAlunoRetornoV1>builder()
                        .clazz(Aluno.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("turmaAtualId"), turma.getId())))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarAlunosDaTurmaRetornoV1 retorno = new PesquisarAlunosDaTurmaRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    private PesquisarAlunosDaTurmaAlunoRetornoV1 paraRetorno(Aluno aluno) {
        PesquisarAlunosDaTurmaAlunoRetornoV1 item = new PesquisarAlunosDaTurmaAlunoRetornoV1();

        item.setUid(aluno.getUid());
        item.setId(aluno.getId());
        item.setNome(aluno.getUsuario().getNome());
        item.setEmail(aluno.getUsuario().getEmail());
        item.setMatriculaRA(aluno.getMatriculaRA());
        item.setDataIngresso(aluno.getDataIngresso());
        item.setStatus(aluno.getStatus());

        return item;
    }
}
