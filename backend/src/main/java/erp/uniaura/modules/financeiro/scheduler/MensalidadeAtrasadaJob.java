package erp.uniaura.modules.financeiro.scheduler;

import cloudsupport.services.jobs.BaseJob;
import cloudsupport.services.jobs.Job;
import cloudsupport.services.jobs.JobParams;
import cloudsupport.services.jobs.RepeatStatus;

import erp.uniaura.modules.financeiro.service.MensalidadeService;

import jakarta.inject.Inject;

@Job
public class MensalidadeAtrasadaJob extends BaseJob {

    @Inject
    private MensalidadeService mensalidadeService;

    @Override
    protected RepeatStatus execute(JobParams parameters, long sequence) {
        int qtd = mensalidadeService.marcarMensalidadesAtrasadas();
        if (qtd > 0) {
            logger.info("Financeiro: {} mensalidade(s) marcadas como ATRASADA.", qtd);
        }
        return RepeatStatus.FINISHED;
    }
}
