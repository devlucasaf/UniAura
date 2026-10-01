package erp.uniaura.modules.atividade.repository;

import erp.uniaura.modules.atividade.model.Atividade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    Page<Atividade> findByTurmaDisciplinaId(Long turmaDisciplinaId, Pageable pageable);
}

