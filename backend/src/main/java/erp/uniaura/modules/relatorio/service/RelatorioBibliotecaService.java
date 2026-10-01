package erp.uniaura.modules.relatorio.service;

import erp.uniaura.modules.biblioteca.exemplar.model.StatusExemplar;
import erp.uniaura.modules.biblioteca.exemplar.repository.ExemplarRepository;
import erp.uniaura.modules.biblioteca.multa.model.StatusMulta;
import erp.uniaura.modules.biblioteca.multa.repository.MultaRepository;
import erp.uniaura.modules.relatorio.dto.RelatorioBibliotecaResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class RelatorioBibliotecaService {

    private static final int ESCALA = 2;
    private static final BigDecimal CEM = new BigDecimal("100");

    private final ExemplarRepository exemplarRepository;
    private final MultaRepository multaRepository;

    // --- CONSOLIDA OS INDICADORES DE UTILIZAÇÃO DO ACERVO E MULTAS PENDENTES ---
    @Transactional(readOnly = true)
    public RelatorioBibliotecaResponseDTO gerar() {
        long total = exemplarRepository.count();
        long disponiveis = exemplarRepository.countByStatus(StatusExemplar.DISPONIVEL);
        long emprestados = exemplarRepository.countByStatus(StatusExemplar.EMPRESTADO);
        long reservados = exemplarRepository.countByStatus(StatusExemplar.RESERVADO);

        return RelatorioBibliotecaResponseDTO.builder()
                .totalExemplares(total)
                .exemplaresDisponiveis(disponiveis)
                .exemplaresEmprestados(emprestados)
                .exemplaresReservados(reservados)
                .taxaUtilizacaoPercentual(percentual(emprestados, total))
                .multasPendentes(multaRepository.countByStatus(StatusMulta.PENDENTE))
                .valorMultasPendentes(multaRepository.somarValorPorStatus(StatusMulta.PENDENTE))
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
