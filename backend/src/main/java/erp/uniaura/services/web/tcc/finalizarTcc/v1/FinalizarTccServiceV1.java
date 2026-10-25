package erp.uniaura.services.web.tcc.finalizarTcc.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.StatusTcc;
import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.services.common.tcc.BuscarTccComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinalizarTccServiceV1 extends BaseService {

    private static final int ESCALA_NOTA = 2;

    private static final BigDecimal NOTA_MINIMA_DE_APROVACAO = BigDecimal.valueOf(6);

    private final BuscarTccComponent buscarTccComponent;

    @Transactional
    public void finalizar(FinalizarTccParamsV1 params) {
        Tcc tcc = buscarTccComponent.buscarPorUid(params.getUid());

        if (tcc.getStatus() != StatusTcc.AGUARDANDO_BANCA) {
            throw new BusinessException("O TCC precisa estar aguardando banca para ser finalizado.");
        }

        List<BigDecimal> notas = em()
                .createQuery("select m.nota from BancaTccMembro m where m.tcc = :tcc and m.nota is not null", BigDecimal.class)
                .setParameter("tcc", tcc)
                .getResultList();

        if (notas.isEmpty()) {
            throw new BusinessException("Nenhum membro da banca lançou nota ainda.");
        }

        BigDecimal soma = BigDecimal.ZERO;
        for (BigDecimal nota : notas) {
            soma = soma.add(nota);
        }

        BigDecimal media = soma.divide(BigDecimal.valueOf(notas.size()), ESCALA_NOTA, RoundingMode.HALF_UP);

        tcc.setNotaFinal(media);

        if (media.compareTo(NOTA_MINIMA_DE_APROVACAO) >= 0) {
            tcc.setStatus(StatusTcc.APROVADO);
        } else {
            tcc.setStatus(StatusTcc.REPROVADO);
        }

        tcc.merge();
    }
}
