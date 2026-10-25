package erp.uniaura.services.common.nota;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Nota;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarNotasDoAlunoComponent extends BaseComponent {

    // --- TODAS AS NOTAS LANÇADAS PARA O ALUNO ---
    public List<Nota> listar(Aluno aluno) {
        return em()
                .createQuery("select n from Nota n where n.aluno = :aluno order by n.lancadaEm", Nota.class)
                .setParameter("aluno", aluno)
                .getResultList();
    }
}
