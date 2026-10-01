package erp.uniaura.modules.biblioteca.exemplar.repository;

import erp.uniaura.modules.biblioteca.exemplar.model.Exemplar;
import erp.uniaura.modules.biblioteca.exemplar.model.StatusExemplar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {

    Optional<Exemplar> findByCodigoBarras(String codigoBarras);

    Page<Exemplar> findByLivroId(Long livroId, Pageable pageable);

    List<Exemplar> findByLivroIdAndStatus(Long livroId, StatusExemplar status);

    long countByLivroId(Long livroId);

    long countByLivroIdAndStatus(Long livroId, StatusExemplar status);

    long countByStatus(StatusExemplar status);

    boolean existsByCodigoBarras(String codigoBarras);
}

