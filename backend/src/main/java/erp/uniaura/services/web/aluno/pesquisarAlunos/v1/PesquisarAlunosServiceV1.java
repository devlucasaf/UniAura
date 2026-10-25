package erp.uniaura.services.web.aluno.pesquisarAlunos.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.turma.ConsultarUidDasTurmasComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PesquisarAlunosServiceV1 extends BaseService {

    private final ConsultarUidDasTurmasComponent consultarUidDasTurmasComponent;

    // --- PESQUISA ALUNOS COM PAGINAÇÃO E FILTRO POR STATUS ---
    @Transactional(readOnly = true)
    public PesquisarAlunosRetornoV1 pesquisar(PesquisarAlunosParamsV1 params) {
        PageableQuery.Result<PesquisarAlunosAlunoRetornoV1> resultado =
                PageableQuery.<Aluno, PesquisarAlunosAlunoRetornoV1>builder()
                        .clazz(Aluno.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            if (params.getStatus() != null) {
                                criteria.where(cb().equal(from.get("status"), params.getStatus()));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarAlunosRetornoV1 retorno = new PesquisarAlunosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A ENTIDADE ALUNO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarAlunosAlunoRetornoV1 paraRetorno(Aluno aluno) {
        Usuario usuario = aluno.getUsuario();

        PesquisarAlunosAlunoRetornoV1 item = new PesquisarAlunosAlunoRetornoV1();

        item.setUid(aluno.getUid());
        item.setNome(usuario.getNome());
        item.setEmail(usuario.getEmail());
        item.setCpf(usuario.getCpf());
        item.setTelefone(usuario.getTelefone());
        item.setDataNascimento(usuario.getDataNascimento());
        item.setMatriculaRA(aluno.getMatriculaRA());
        item.setDataIngresso(aluno.getDataIngresso());
        item.setStatus(aluno.getStatus());
        item.setCurso(aluno.getCurso());

        if (aluno.getTurmaAtualId() != null) {
            Map<Long, Long> uids = consultarUidDasTurmasComponent.uidsPorId(Collections.singleton(aluno.getTurmaAtualId()));

            item.setTurmaAtualUid(uids.get(aluno.getTurmaAtualId()));
        }

        return item;
    }
}
