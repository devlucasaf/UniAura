package erp.uniaura.modules.responsavel.repository;

import erp.uniaura.modules.responsavel.model.ResponsavelAluno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponsavelAlunoRepository extends JpaRepository<ResponsavelAluno, Long> {

    List<ResponsavelAluno> findByResponsavelId(Long responsavelId);

    List<ResponsavelAluno> findByAlunoId(Long alunoId);

    Optional<ResponsavelAluno> findByResponsavelIdAndAlunoId(Long responsavelId, Long alunoId);

    boolean existsByResponsavelIdAndAlunoId(Long responsavelId, Long alunoId);
}

