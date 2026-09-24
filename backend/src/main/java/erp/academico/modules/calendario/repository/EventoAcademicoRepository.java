package erp.academico.modules.calendario.repository;

import erp.academico.modules.calendario.model.EventoAcademico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventoAcademicoRepository extends JpaRepository<EventoAcademico, UUID> {

    List<EventoAcademico> findByPublicoTrueOrderByDataInicioAsc();
}
