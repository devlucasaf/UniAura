package erp.uniaura.modules.calendario.repository;

import erp.uniaura.modules.calendario.model.EventoAcademico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoAcademicoRepository extends JpaRepository<EventoAcademico, Long> {

    List<EventoAcademico> findByPublicoTrueOrderByDataInicioAsc();
}
