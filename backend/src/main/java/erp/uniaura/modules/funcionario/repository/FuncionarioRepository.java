package erp.uniaura.modules.funcionario.repository;

import erp.uniaura.modules.funcionario.model.CargoFuncionario;
import erp.uniaura.modules.funcionario.model.Funcionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {

    Page<Funcionario> findByCargo(CargoFuncionario cargo, Pageable pageable);
}

