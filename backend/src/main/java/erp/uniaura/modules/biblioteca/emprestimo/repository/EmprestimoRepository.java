package erp.uniaura.modules.biblioteca.emprestimo.repository;

import erp.uniaura.modules.biblioteca.emprestimo.model.Emprestimo;
import erp.uniaura.modules.biblioteca.emprestimo.model.StatusEmprestimo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, UUID> {

    // --- CONTA OS EMPRÉSTIMOS DE UM USUÁRIO QUE POSSUEM O STATUS INFORMADO ---
    long countByUsuarioIdAndStatus(UUID usuarioId, StatusEmprestimo status);

    // --- BUSCA OS EMPRÉSTIMOS DE UM USUÁRIO UTILIZANDO PAGINAÇÃO ---
    Page<Emprestimo> findByUsuarioId(UUID usuarioId, Pageable pageable);

    // --- BUSCA OS EMPRÉSTIMOS DE UM USUÁRIO QUE POSSUEM O STATUS INFORMADO ---
    List<Emprestimo> findByUsuarioIdAndStatus(UUID usuarioId, StatusEmprestimo status);

    // --- BUSCA EMPRÉSTIMOS COM O STATUS INFORMADO E DEVOLUÇÃO PREVISTA ANTERIOR À DATA RECEBIDA ---
    List<Emprestimo> findByStatusAndDataDevolucaoPrevistaBefore(StatusEmprestimo status, LocalDateTime data);
}

