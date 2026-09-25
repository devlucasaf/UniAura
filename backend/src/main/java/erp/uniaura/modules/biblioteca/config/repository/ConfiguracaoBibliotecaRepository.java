package erp.uniaura.modules.biblioteca.config.repository;

import erp.uniaura.modules.biblioteca.config.model.ConfiguracaoBiblioteca;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConfiguracaoBibliotecaRepository extends JpaRepository<ConfiguracaoBiblioteca, UUID> {
}

