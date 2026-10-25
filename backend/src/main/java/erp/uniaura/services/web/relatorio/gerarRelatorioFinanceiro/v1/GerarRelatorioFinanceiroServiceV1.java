package erp.uniaura.services.web.relatorio.gerarRelatorioFinanceiro.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.StatusMensalidade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class GerarRelatorioFinanceiroServiceV1 extends BaseService {

    private static final int        ESCALA = 2;
    private static final BigDecimal CEM    = new BigDecimal("100");

    // --- CONSOLIDA OS INDICADORES DE INADIMPLÊNCIA E ARRECADAÇÃO DAS MENSALIDADES ---
    @Transactional(readOnly = true)
    public GerarRelatorioFinanceiroRetornoV1 gerar() {
        long pendentes = contarPorStatus(StatusMensalidade.PENDENTE);
        long atrasadas = contarPorStatus(StatusMensalidade.ATRASADA);
        long pagas = contarPorStatus(StatusMensalidade.PAGA);

        GerarRelatorioFinanceiroRetornoV1 retorno = new GerarRelatorioFinanceiroRetornoV1();

        retorno.setTotalMensalidadesPendentes(pendentes);
        retorno.setTotalMensalidadesAtrasadas(atrasadas);
        retorno.setTotalMensalidadesPagas(pagas);
        retorno.setTotalMensalidadesCanceladas(contarPorStatus(StatusMensalidade.CANCELADA));
        retorno.setValorEmAbertoPendente(somarValorPorStatus(StatusMensalidade.PENDENTE));
        retorno.setValorEmAtraso(somarValorPorStatus(StatusMensalidade.ATRASADA));
        retorno.setValorRecebido(somarValorPorStatus(StatusMensalidade.PAGA));
        retorno.setTaxaInadimplenciaPercentual(percentual(atrasadas, pendentes + atrasadas + pagas));

        return retorno;
    }

    // --- CONTA AS MENSALIDADES PELO STATUS INFORMADO ---
    private long contarPorStatus(StatusMensalidade status) {
        return em()
                .createQuery("select count(m) from Mensalidade m where m.status = :status", Long.class)
                .setParameter("status", status)
                .getSingleResult();
    }

    // --- ZERO QUANDO NÃO HÁ MENSALIDADE NO STATUS ---
    private BigDecimal somarValorPorStatus(StatusMensalidade status) {
        return em()
                .createQuery("select coalesce(sum(m.valor), 0) from Mensalidade m where m.status = :status", BigDecimal.class)
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
