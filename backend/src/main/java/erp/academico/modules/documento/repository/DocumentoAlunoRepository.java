package erp.academico.modules.documento.repository;

import erp.academico.modules.documento.model.DocumentoAluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentoAlunoRepository extends JpaRepository<DocumentoAluno, UUID> {

    Page<DocumentoAluno> findByAlunoId(UUID alunoId, Pageable pageable);
}
