package erp.uniaura.modules.certificado.repository;

import erp.uniaura.modules.certificado.model.CertificadoEmitido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificadoEmitidoRepository extends JpaRepository<CertificadoEmitido, Long> {

    Optional<CertificadoEmitido> findByCodigoVerificacao(String codigoVerificacao);

    Page<CertificadoEmitido> findByAlunoId(Long alunoId, Pageable pageable);
}
