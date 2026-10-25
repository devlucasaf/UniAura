package erp.uniaura.services.web.tcc.consultarMeuTcc.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.StatusTcc;
import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarMeuTccServiceV1 extends BaseService {

    // --- TCC EM ANDAMENTO É O QUE NÃO ESTÁ APROVADO, REPROVADO NEM CANCELADO ---
    private static final List<StatusTcc> STATUS_FINALIZADOS = List.of(StatusTcc.APROVADO, StatusTcc.REPROVADO, StatusTcc.CANCELADO);

    private final BuscarAlunoComponent buscarAlunoComponent;

    @Transactional(readOnly = true)
    public ConsultarMeuTccRetornoV1 consultar(Long usuarioId) {
        Aluno aluno = buscarAlunoComponent.buscarPorUsuarioId(usuarioId);

        List<Tcc> emAndamento = em()
                .createQuery("select t from Tcc t where t.aluno = :aluno and t.status not in :finalizados", Tcc.class)
                .setParameter("aluno", aluno)
                .setParameter("finalizados", STATUS_FINALIZADOS)
                .setMaxResults(1)
                .getResultList();

        if (emAndamento.isEmpty()) {
            throw new BusinessException("Você não possui TCC em andamento.");
        }

        Tcc tcc = emAndamento.get(0);

        ConsultarMeuTccRetornoV1 retorno = new ConsultarMeuTccRetornoV1();

        retorno.setUid(tcc.getUid());
        retorno.setTitulo(tcc.getTitulo());
        retorno.setProfessorOrientadorNome(tcc.getProfessorOrientador().getUsuario().getNome());

        if (tcc.getProfessorCoorientador() != null) {
            retorno.setProfessorCoorientadorNome(tcc.getProfessorCoorientador().getUsuario().getNome());
        }

        retorno.setPeriodoLetivo(tcc.getPeriodoLetivo());
        retorno.setStatus(tcc.getStatus());
        retorno.setDataDefesa(tcc.getDataDefesa());

        return retorno;
    }
}
