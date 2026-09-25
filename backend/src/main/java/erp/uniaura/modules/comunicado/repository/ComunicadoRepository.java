package erp.uniaura.modules.comunicado.repository;

import erp.uniaura.modules.comunicado.model.Comunicado;
import erp.uniaura.modules.comunicado.model.PublicoAlvoComunicado;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ComunicadoRepository extends JpaRepository<Comunicado, UUID> {

    Page<Comunicado> findByPublicoAlvo(PublicoAlvoComunicado publicoAlvo, Pageable pageable);

    // --- MURAL DO ALUNO: COMUNICADOS GERAIS/PARA ALUNOS + OS DIRECIONADOS À TURMA DELE ---
    List<Comunicado> findTop30ByPublicoAlvoInOrderByCriadoEmDesc(Collection<PublicoAlvoComunicado> publicosAlvo);

    List<Comunicado> findTop30ByPublicoAlvoAndTurmaIdOrderByCriadoEmDesc(PublicoAlvoComunicado publicoAlvo, UUID turmaId);
}
