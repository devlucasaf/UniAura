package erp.uniaura.modules.documento.repository;

import erp.uniaura.modules.documento.model.DocumentoAluno;
import erp.uniaura.modules.documento.model.TipoDocumento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoAlunoRepository extends JpaRepository<DocumentoAluno, Long> {

    Page<DocumentoAluno> findByAlunoId(Long alunoId, Pageable pageable);

    long countByAlunoIdAndTipo(Long alunoId, TipoDocumento tipo);

    List<DocumentoAluno> findByAlunoIdAndTipoOrderByVersaoDesc(Long alunoId, TipoDocumento tipo);
}
