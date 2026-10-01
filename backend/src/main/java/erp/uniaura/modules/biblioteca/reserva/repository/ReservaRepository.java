package erp.uniaura.modules.biblioteca.reserva.repository;

import erp.uniaura.modules.biblioteca.reserva.model.Reserva;
import erp.uniaura.modules.biblioteca.reserva.model.StatusReserva;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByLivroIdAndStatusOrderByPosicaoFilaAsc(Long livroId, StatusReserva status);

    Optional<Reserva> findFirstByLivroIdAndStatusOrderByPosicaoFilaAsc(Long livroId, StatusReserva status);

    List<Reserva> findByUsuarioIdOrderByDataReservaDesc(Long usuarioId);

    boolean existsByLivroIdAndUsuarioIdAndStatus(Long livroId, Long usuarioId, StatusReserva status);

    long countByLivroIdAndStatus(Long livroId, StatusReserva status);

    List<Reserva> findByStatusAndPrazoRetiradaBefore(StatusReserva status, LocalDateTime data);
}

