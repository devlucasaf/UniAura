package erp.uniaura.services.jobs.marcarEmprestimosVencidos;

import cloudsupport.services.jobs.BaseJob;
import cloudsupport.services.jobs.Job;
import cloudsupport.services.jobs.JobParams;
import cloudsupport.services.jobs.RepeatStatus;

import erp.uniaura.services.common.biblioteca.MarcarEmprestimosVencidosComponent;

import jakarta.inject.Inject;

@Job
public class MarcarEmprestimosVencidosJob extends BaseJob {

    @Inject
    private MarcarEmprestimosVencidosComponent marcarEmprestimosVencidosComponent;

    // --- MARCA EMPRÉSTIMOS VENCIDOS E FINALIZA O JOB ---
    @Override
    protected RepeatStatus execute(JobParams parameters, long sequence) {
        int quantidade = marcarEmprestimosVencidosComponent.marcar();

        if (quantidade > 0) {
            logger.info("Biblioteca: {} empréstimo(s) marcados como ATRASADO.", quantidade);
        }

        return RepeatStatus.FINISHED;
    }
}