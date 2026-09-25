package erp.uniaura.modules.biblioteca.scheduler;

import erp.uniaura.modules.biblioteca.emprestimo.service.EmprestimoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmprestimoScheduler {

    private final EmprestimoService emprestimoService;

    // --- MARCA DIARIAMENTE OS EMPRÉSTIMOS VENCIDOS COMO ATRASADOS ---
    @Scheduled(cron = "0 0 3 * * *")
    public void marcarEmprestimosVencidos() {
        int qtd = emprestimoService.marcarEmprestimosVencidos();
        if (qtd > 0) {
            log.info("Biblioteca: {} empréstimo(s) marcados como ATRASADO.", qtd);
        }
    }
}

