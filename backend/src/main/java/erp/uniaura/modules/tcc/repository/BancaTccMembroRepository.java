package erp.uniaura.modules.tcc.repository;

import erp.uniaura.modules.tcc.model.BancaTccMembro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BancaTccMembroRepository extends JpaRepository<BancaTccMembro, Long> {

    List<BancaTccMembro> findByTccId(Long tccId);

    boolean existsByTccIdAndProfessorId(Long tccId, Long professorId);
}
