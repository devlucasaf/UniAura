package erp.uniaura.modules.responsavel.repository;

import erp.uniaura.modules.responsavel.model.Responsavel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, UUID> {
}

