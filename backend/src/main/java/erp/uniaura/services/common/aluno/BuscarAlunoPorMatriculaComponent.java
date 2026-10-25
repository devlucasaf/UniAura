package erp.uniaura.services.common.aluno;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Aluno;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BuscarAlunoPorMatriculaComponent extends BaseComponent {

    // --- PELO RA, QUE É ÚNICO; VAZIO QUANDO NÃO EXISTE ---
    public Optional<Aluno> buscar(String matriculaRA) {
        List<Aluno> encontrados = em()
                .createQuery("select a from Aluno a where a.matriculaRA = :matriculaRA", Aluno.class)
                .setParameter("matriculaRA", matriculaRA)
                .getResultList();

        if (encontrados.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(encontrados.get(0));
    }
}