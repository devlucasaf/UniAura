package erp.uniaura.services.web.biblioteca.reservarLivro.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Livro;
import erp.uniaura.persistence.entities.Reserva;
import erp.uniaura.persistence.entities.StatusExemplar;
import erp.uniaura.persistence.entities.StatusReserva;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.biblioteca.BuscarLivroComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservarLivroServiceV1 extends BaseService {

    private final BuscarLivroComponent buscarLivroComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- RESERVA O LIVRO SEM EXEMPLAR DISPONÍVEL, ENTRANDO NA FILA ---
    @Transactional
    public ReservarLivroRetornoV1 reservar(Long usuarioId, ReservarLivroParamsV1 params) {
        Livro livro = buscarLivroComponent.buscarPorUid(params.getLivroUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        // --- COM EXEMPLAR DISPONÍVEL, NÃO PRECISA RESERVAR ---
        Long disponiveis = em()
                .createQuery("select count(e) from Exemplar e where e.livro = :livro and e.status = :status", Long.class)
                .setParameter("livro", livro)
                .setParameter("status", StatusExemplar.DISPONIVEL)
                .getSingleResult();

        if (disponiveis > 0) {
            throw new BusinessException("Existe(m) exemplar(es) disponível(is) para este livro. Faça o empréstimo diretamente.");
        }

        Long jaReservou = em()
                .createQuery("select count(r) from Reserva r where r.livro = :livro and r.usuario = :usuario and r.status = :status", Long.class)
                .setParameter("livro", livro)
                .setParameter("usuario", autenticado)
                .setParameter("status", StatusReserva.AGUARDANDO)
                .getSingleResult();

        if (jaReservou > 0) {
            throw new BusinessException("Você já possui uma reserva ativa para este livro.");
        }

        Long naFila = em()
                .createQuery("select count(r) from Reserva r where r.livro = :livro and r.status = :status", Long.class)
                .setParameter("livro", livro)
                .setParameter("status", StatusReserva.AGUARDANDO)
                .getSingleResult();

        Reserva reserva = new Reserva();

        reserva.setLivro(livro);
        reserva.setUsuario(autenticado);
        reserva.setStatus(StatusReserva.AGUARDANDO);
        reserva.setPosicaoFila(naFila.intValue() + 1);
        reserva.persist();

        ReservarLivroRetornoV1 retorno = new ReservarLivroRetornoV1();

        retorno.setUid(reserva.getUid());
        retorno.setPosicaoFila(reserva.getPosicaoFila());

        return retorno;
    }
}
