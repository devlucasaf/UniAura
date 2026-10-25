package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Livro;
import erp.uniaura.persistence.entities.Reserva;
import erp.uniaura.persistence.entities.StatusReserva;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReordenarFilaDeReservasComponent extends BaseComponent {

    // --- REFAZ AS POSIÇÕES 1, 2, 3... DE QUEM AINDA AGUARDA NA FILA DO LIVRO ---
    public void reordenar(Livro livro) {
        List<Reserva> fila = em()
                .createQuery("select r from Reserva r where r.livro = :livro and r.status = :status order by r.posicaoFila", Reserva.class)
                .setParameter("livro", livro)
                .setParameter("status", StatusReserva.AGUARDANDO)
                .getResultList();

        int posicao = 1;

        for (Reserva reserva : fila) {
            reserva.setPosicaoFila(posicao);
            reserva.merge();
            posicao = posicao + 1;
        }
    }
}
