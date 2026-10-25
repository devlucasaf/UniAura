package erp.uniaura.services.web.relatorio.gerarRelatorioDaBiblioteca.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.StatusExemplar;
import erp.uniaura.persistence.entities.StatusMulta;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class GerarRelatorioDaBibliotecaServiceV1 extends BaseService {

    private static final int        ESCALA = 2;
    private static final BigDecimal CEM    = new BigDecimal("100");

    // --- CONSOLIDA A UTILIZAÇÃO DO ACERVO E AS MULTAS PENDENTES ---
    @Transactional(readOnly = true)
    public GerarRelatorioDaBibliotecaRetornoV1 gerar() {
        long total = em().createQuery("select count(e) from Exemplar e", Long.class).getSingleResult();
        long emprestados = contarExemplares(StatusExemplar.EMPRESTADO);

        GerarRelatorioDaBibliotecaRetornoV1 retorno = new GerarRelatorioDaBibliotecaRetornoV1();

        retorno.setTotalExemplares(total);
        retorno.setExemplaresDisponiveis(contarExemplares(StatusExemplar.DISPONIVEL));
        retorno.setExemplaresEmprestados(emprestados);
        retorno.setExemplaresReservados(contarExemplares(StatusExemplar.RESERVADO));
        retorno.setTaxaUtilizacaoPercentual(percentual(emprestados, total));

        retorno.setMultasPendentes(em()
                .createQuery("select count(m) from Multa m where m.status = :status", Long.class)
                .setParameter("status", StatusMulta.PENDENTE)
                .getSingleResult());

        retorno.setValorMultasPendentes(em()
                .createQuery("select coalesce(sum(m.valor), 0) from Multa m where m.status = :status", BigDecimal.class)
                .setParameter("status", StatusMulta.PENDENTE)
                .getSingleResult());

        return retorno;
    }

    // --- CONTA OS EXEMPLARES PELO STATUS INFORMADO ---
    private long contarExemplares(StatusExemplar status) {
        return em()
                .createQuery("select count(e) from Exemplar e where e.status = :status", Long.class)
                .setParameter("status", status)
                .getSingleResult();
    }

    // --- DIVIDE COM PROTEÇÃO CONTRA DENOMINADOR ZERO E DEVOLVE O VALOR EM PERCENTUAL ---
    private BigDecimal percentual(long parte, long total) {
        if (total <= 0) {
            return BigDecimal.ZERO.setScale(ESCALA, RoundingMode.HALF_UP);
        }

        return BigDecimal.valueOf(parte)
                .multiply(CEM)
                .divide(BigDecimal.valueOf(total), ESCALA, RoundingMode.HALF_UP);
    }
}
