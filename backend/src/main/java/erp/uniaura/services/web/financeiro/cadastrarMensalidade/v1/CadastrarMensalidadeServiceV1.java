package erp.uniaura.services.web.financeiro.cadastrarMensalidade.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.StatusMensalidade;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarMensalidadeServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- LANÇA A MENSALIDADE DE UMA COMPETÊNCIA PARA O ALUNO, UMA POR COMPETÊNCIA ---
    @Transactional
    public Uid cadastrar(CadastrarMensalidadeParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());

        Long existentes = em()
                .createQuery("select count(m) from Mensalidade m where m.aluno = :aluno and m.competencia = :competencia", Long.class)
                .setParameter("aluno", aluno)
                .setParameter("competencia", params.getCompetencia())
                .getSingleResult();

        if (existentes > 0) {
            throw new BusinessException("Já existe uma mensalidade para este aluno na competência " + params.getCompetencia() + ".");
        }

        Mensalidade mensalidade = new Mensalidade();

        mensalidade.setAluno(aluno);
        mensalidade.setCompetencia(params.getCompetencia());
        mensalidade.setValor(params.getValor());
        mensalidade.setVencimento(params.getVencimento());
        mensalidade.setDescricao(params.getDescricao());

        if (params.getStatus() == null) {
            mensalidade.setStatus(StatusMensalidade.PENDENTE);
        } else {
            mensalidade.setStatus(params.getStatus());
        }

        mensalidade.persist();

        return new Uid(mensalidade.getUid());
    }
}
