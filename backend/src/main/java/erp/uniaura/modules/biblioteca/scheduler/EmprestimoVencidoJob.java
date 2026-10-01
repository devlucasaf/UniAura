package erp.uniaura.modules.biblioteca.scheduler;

import cloudsupport.services.jobs.BaseJob;
import cloudsupport.services.jobs.Job;
import cloudsupport.services.jobs.JobParams;
import cloudsupport.services.jobs.RepeatStatus;

import erp.uniaura.modules.biblioteca.emprestimo.service.EmprestimoService;

import jakarta.inject.Inject;

@Job
public class EmprestimoVencidoJob extends BaseJob {

    @Inject
    private EmprestimoService emprestimoService;

    @Override
    protected RepeatStatus execute(JobParams parameters, long sequence) {
        int qtd = emprestimoService.marcarEmprestimosVencidos();
        if (qtd > 0) {
            logger.info("Biblioteca: {} empréstimo(s) marcados como ATRASADO.", qtd);
        }
        return RepeatStatus.FINISHED;
    }
}
