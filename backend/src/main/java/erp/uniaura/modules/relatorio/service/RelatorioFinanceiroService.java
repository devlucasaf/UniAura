package erp.uniaura.modules.relatorio.service;

import erp.uniaura.modules.financeiro.model.StatusMensalidade;
import erp.uniaura.modules.financeiro.repository.MensalidadeRepository;
import erp.uniaura.modules.relatorio.dto.RelatorioFinanceiroResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class RelatorioFinanceiroService {

    private static final int ESCALA = 2;
    private static final BigDecimal CEM = new BigDecimal("100");

    private final MensalidadeRepository mensalidadeRepository;

    // --- CONSOLIDA OS INDICADORES DE INADIMPLÊNCIA E ARRECADAÇÃO DAS MENSALIDADES ---
    @Transactional(readOnly = true)
    public RelatorioFinanceiroResponseDTO gerar() {
        long pendentes = mensalidadeRepository.countByStatus(StatusMensalidade.PENDENTE);
        long atrasadas = mensalidadeRepository.countByStatus(StatusMensalidade.ATRASADA);
        long pagas = mensalidadeRepository.countByStatus(StatusMensalidade.PAGA);
        long canceladas = mensalidadeRepository.countByStatus(StatusMensalidade.CANCELADA);

        BigDecimal valorPendente = mensalidadeRepository.somarValorPorStatus(StatusMensalidade.PENDENTE);
        BigDecimal valorAtraso = mensalidadeRepository.somarValorPorStatus(StatusMensalidade.ATRASADA);
        BigDecimal valorRecebido = mensalidadeRepository.somarValorPorStatus(StatusMensalidade.PAGA);

        long totalConsiderado = pendentes + atrasadas + pagas;

        return RelatorioFinanceiroResponseDTO.builder()
                .totalMensalidadesPendentes(pendentes)
                .totalMensalidadesAtrasadas(atrasadas)
                .totalMensalidadesPagas(pagas)
                .totalMensalidadesCanceladas(canceladas)
                .valorEmAbertoPendente(valorPendente)
                .valorEmAtraso(valorAtraso)
                .valorRecebido(valorRecebido)
                .taxaInadimplenciaPercentual(percentual(atrasadas, totalConsiderado))
                .build();
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
