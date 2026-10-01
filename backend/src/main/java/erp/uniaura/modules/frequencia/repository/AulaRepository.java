package erp.uniaura.modules.frequencia.repository;

import erp.uniaura.modules.frequencia.model.Aula;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Aula> findByTurmaDisciplinaId(Long turmaDisciplinaId);
}

