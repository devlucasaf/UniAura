package erp.uniaura.services.web.estagio.consultarMeuEstagio.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Estagio;
import erp.uniaura.persistence.entities.StatusEstagio;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarMeuEstagioServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- RETORNA O ESTÁGIO EM ANDAMENTO DO ALUNO LOGADO ---
    @Transactional(readOnly = true)
    public ConsultarMeuEstagioRetornoV1 consultar(Long usuarioId) {
        Aluno aluno = buscarAlunoComponent.buscarPorUsuarioId(usuarioId);

        List<Estagio> emAndamento = em()
                .createQuery("select e from Estagio e where e.aluno = :aluno and e.status = :status", Estagio.class)
                .setParameter("aluno", aluno)
                .setParameter("status", StatusEstagio.EM_ANDAMENTO)
                .setMaxResults(1)
                .getResultList();

        if (emAndamento.isEmpty()) {
            throw new BusinessException("Você não possui estágio em andamento.");
        }

        Estagio estagio = emAndamento.get(0);

        ConsultarMeuEstagioRetornoV1 retorno = new ConsultarMeuEstagioRetornoV1();

        retorno.setUid(estagio.getUid());
        retorno.setEmpresaConcedente(estagio.getEmpresaConcedente());
        retorno.setSupervisorEmpresa(estagio.getSupervisorEmpresa());

        if (estagio.getProfessorOrientador() != null) {
            retorno.setProfessorOrientadorNome(estagio.getProfessorOrientador().getUsuario().getNome());
        }

        retorno.setDataInicio(estagio.getDataInicio());
        retorno.setDataFimPrevista(estagio.getDataFimPrevista());
        retorno.setCargaHorariaTotal(estagio.getCargaHorariaTotal());
        retorno.setCargaHorariaCumprida(estagio.getCargaHorariaCumprida());
        retorno.setStatus(estagio.getStatus());
        retorno.setTermoCompromissoUrl(estagio.getTermoCompromissoUrl());

        return retorno;
    }
}
