package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Exemplar;
import erp.uniaura.persistence.entities.Livro;
import erp.uniaura.persistence.entities.Reserva;
import erp.uniaura.persistence.entities.StatusExemplar;
import erp.uniaura.persistence.entities.StatusReserva;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpirarReservasNaoRetiradasComponent extends BaseComponent {

    private static final int PRAZO_DE_RETIRADA_DA_RESERVA_EM_DIAS = 2;

    private final ReordenarFilaDeReservasComponent reordenarFilaDeReservasComponent;

    // --- RESERVAS CUJO PRAZO DE RETIRADA PASSOU EXPIRAM, E O EXEMPLAR VAI PARA O PRÓXIMO DA FILA OU FICA DISPONÍVEL ---
    @Transactional
    public int expirar() {
        List<Reserva> expiradas = em()
                .createQuery("select r from Reserva r where r.status = :status and r.prazoRetirada < :agora", Reserva.class)
                .setParameter("status", StatusReserva.AGUARDANDO)
                .setParameter("agora", LocalDateTime.now())
                .getResultList();

        for (Reserva reserva : expiradas) {
            reserva.setStatus(StatusReserva.EXPIRADA);
            reserva.merge();

            reordenarFilaDeReservasComponent.reordenar(reserva.getLivro());
            transferirOuLiberarExemplar(reserva.getLivro());
        }

        return expiradas.size();
    }

    // --- PASSA O EXEMPLAR RESERVADO PARA O PRÓXIMO DA FILA OU, SEM FILA, O LIBERA ---
    private void transferirOuLiberarExemplar(Livro livro) {
        List<Exemplar> reservados = em()
                .createQuery("select e from Exemplar e where e.livro = :livro and e.status = :status", Exemplar.class)
                .setParameter("livro", livro)
                .setParameter("status", StatusExemplar.RESERVADO)
                .setMaxResults(1)
                .getResultList();

        if (reservados.isEmpty()) {
            return;
        }

        List<Reserva> proximaDaFila = em()
                .createQuery("select r from Reserva r where r.livro = :livro and r.status = :status order by r.posicaoFila", Reserva.class)
                .setParameter("livro", livro)
                .setParameter("status", StatusReserva.AGUARDANDO)
                .setMaxResults(1)
                .getResultList();

        if (proximaDaFila.isEmpty()) {
            Exemplar exemplar = reservados.get(0);

            exemplar.setStatus(StatusExemplar.DISPONIVEL);
            exemplar.merge();
        } else {
            Reserva proxima = proximaDaFila.get(0);

            proxima.setPrazoRetirada(LocalDateTime.now().plusDays(PRAZO_DE_RETIRADA_DA_RESERVA_EM_DIAS));
            proxima.merge();
        }
    }
}
