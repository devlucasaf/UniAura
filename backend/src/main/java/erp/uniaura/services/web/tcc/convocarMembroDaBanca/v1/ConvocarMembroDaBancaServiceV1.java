package erp.uniaura.services.web.tcc.convocarMembroDaBanca.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.BancaTccMembro;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;
import erp.uniaura.services.common.tcc.BuscarTccComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConvocarMembroDaBancaServiceV1 extends BaseService {

    private final BuscarTccComponent       buscarTccComponent;
    private final BuscarProfessorComponent buscarProfessorComponent;

    @Transactional
    public Uid convocar(ConvocarMembroDaBancaParamsV1 params) {
        Tcc tcc = buscarTccComponent.buscarPorUid(params.getTccUid());
        Professor professor = buscarProfessorComponent.buscarPorUid(params.getProfessorUid());

        Long jaConvocado = em()
                .createQuery("select count(m) from BancaTccMembro m where m.tcc = :tcc and m.professor = :professor", Long.class)
                .setParameter("tcc", tcc)
                .setParameter("professor", professor)
                .getSingleResult();

        if (jaConvocado > 0) {
            throw new BusinessException("Este professor já compõe a banca deste TCC.");
        }

        BancaTccMembro membro = new BancaTccMembro();

        membro.setTcc(tcc);
        membro.setProfessor(professor);
        membro.persist();

        return new Uid(membro.getUid());
    }
}
