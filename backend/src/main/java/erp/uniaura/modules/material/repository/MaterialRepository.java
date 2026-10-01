package erp.uniaura.modules.material.repository;

import erp.uniaura.modules.material.model.Material;
import erp.uniaura.modules.material.model.TipoMaterial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    Page<Material> findByTurmaDisciplinaId(Long turmaDisciplinaId, Pageable pageable);

    Page<Material> findByTurmaDisciplinaIdAndTipo(Long turmaDisciplinaId, TipoMaterial tipo, Pageable pageable);

    @Query("""
            SELECT m FROM Material m
             WHERE m.turmaDisciplina.turma.id = :turmaId
               AND m.turmaDisciplina.disciplina.id = :disciplinaId
            """)
    Page<Material> findByTurmaIdAndDisciplinaId(@Param("turmaId") Long turmaId,
                                                @Param("disciplinaId") Long disciplinaId,
                                                Pageable pageable
    );
}

