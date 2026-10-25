package erp.uniaura.services.web.biblioteca.devolverEmprestimo.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Emprestimo;
import erp.uniaura.persistence.entities.Exemplar;
import erp.uniaura.persistence.entities.Multa;
import erp.uniaura.persistence.entities.Reserva;
import erp.uniaura.persistence.entities.StatusEmprestimo;
import erp.uniaura.persistence.entities.StatusExemplar;
import erp.uniaura.persistence.entities.StatusMulta;
import erp.uniaura.persistence.entities.StatusReserva;
import erp.uniaura.services.common.biblioteca.BuscarEmprestimoComponent;
import erp.uniaura.services.common.biblioteca.CalcularDiasDeAtrasoComponent;
import erp.uniaura.services.common.biblioteca.ObterConfiguracaoDaBibliotecaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DevolverEmprestimoServiceV1 extends BaseService {

    // --- DIAS QUE O PRIMEIRO DA FILA TEM PARA RETIRAR O EXEMPLAR RESERVADO ---
    private static final int PRAZO_DE_RETIRADA_DA_RESERVA_EM_DIAS = 2;

    private final BuscarEmprestimoComponent buscarEmprestimoComponent;
    private final CalcularDiasDeAtrasoComponent calcularDiasDeAtrasoComponent;
    private final ObterConfiguracaoDaBibliotecaComponent obterConfiguracaoDaBibliotecaComponent;

    // --- REGISTRA A DEVOLUÇÃO, GERA MULTA SE HOUVER ATRASO E LIBERA O EXEMPLAR ---
    @Transactional
    public void devolver(DevolverEmprestimoParamsV1 params) {
        Emprestimo emprestimo = buscarEmprestimoComponent.buscarPorUid(params.getUid());

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new BusinessException("Este empréstimo já foi devolvido.");
        }

        LocalDateTime agora = LocalDateTime.now();

        emprestimo.setDataDevolucaoEfetiva(agora);
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.merge();

        int diasAtraso = calcularDiasDeAtrasoComponent.calcular(emprestimo.getDataDevolucaoPrevista(), agora);

        if (diasAtraso > 0) {
            gerarMulta(emprestimo, diasAtraso);
        }

        liberarOuReservarExemplar(emprestimo.getExemplar(), agora);
    }

    // --- O ATRASO VIRA MULTA PENDENTE ---
    private void gerarMulta(Emprestimo emprestimo, int diasAtraso) {
        BigDecimal valorPorDia = obterConfiguracaoDaBibliotecaComponent.obter().getValorMultaDia();

        Multa multa = new Multa();

        multa.setEmprestimo(emprestimo);
        multa.setValor(valorPorDia.multiply(BigDecimal.valueOf(diasAtraso)));
        multa.setDiasAtraso(diasAtraso);
        multa.setStatus(StatusMulta.PENDENTE);
        multa.persist();
    }

    // --- COM FILA DE RESERVAS, O EXEMPLAR FICA RESERVADO PARA O PRIMEIRO; SEM FILA, VOLTA A FICAR DISPONÍVEL ---
    private void liberarOuReservarExemplar(Exemplar exemplar, LocalDateTime agora) {
        List<Reserva> primeiraDaFila = em()
                .createQuery("select r from Reserva r where r.livro = :livro and r.status = :status order by r.posicaoFila", Reserva.class)
                .setParameter("livro", exemplar.getLivro())
                .setParameter("status", StatusReserva.AGUARDANDO)
                .setMaxResults(1)
                .getResultList();

        if (primeiraDaFila.isEmpty()) {
            exemplar.setStatus(StatusExemplar.DISPONIVEL);
        } else {
            Reserva reserva = primeiraDaFila.get(0);

            reserva.setPrazoRetirada(agora.plusDays(PRAZO_DE_RETIRADA_DA_RESERVA_EM_DIAS));
            reserva.merge();

            exemplar.setStatus(StatusExemplar.RESERVADO);
        }

        exemplar.merge();
    }
}
