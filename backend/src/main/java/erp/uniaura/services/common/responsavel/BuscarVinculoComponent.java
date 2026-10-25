package erp.uniaura.services.common.responsavel;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Responsavel;
import erp.uniaura.persistence.entities.ResponsavelAluno;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BuscarVinculoComponent extends BaseComponent {

    // --- BUSCA VÍNCULO ENTRE RESPONSÁVEL E ALUNO ---
    public Optional<ResponsavelAluno> buscar(Responsavel responsavel, Aluno aluno) {
        List<ResponsavelAluno> vinculos = em()
                .createQuery("select v from ResponsavelAluno v where v.responsavel = :responsavel and v.aluno = :aluno", ResponsavelAluno.class)
                .setParameter("responsavel", responsavel)
                .setParameter("aluno", aluno)
                .getResultList();

        if (vinculos.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(vinculos.get(0));
    }

    // --- LISTA VÍNCULOS DE UM RESPONSÁVEL ORDENADOS POR CRIAÇÃO ---
    public List<ResponsavelAluno> listarDoResponsavel(Responsavel responsavel) {
        return em()
                .createQuery("select v from ResponsavelAluno v where v.responsavel = :responsavel order by v.criadoEm", ResponsavelAluno.class)
                .setParameter("responsavel", responsavel)
                .getResultList();
    }
}
