package erp.uniaura.services.jobs.marcarMensalidadesAtrasadas;

import cloudsupport.services.jobs.BaseJob;
import cloudsupport.services.jobs.Job;
import cloudsupport.services.jobs.JobParams;
import cloudsupport.services.jobs.RepeatStatus;

import erp.uniaura.services.common.financeiro.MarcarMensalidadesAtrasadasComponent;

import jakarta.inject.Inject;

@Job
public class MarcarMensalidadesAtrasadasJob extends BaseJob {

    @Inject
    private MarcarMensalidadesAtrasadasComponent marcarMensalidadesAtrasadasComponent;

    // --- MARCA MENSALIDADES ATRASADAS E FINALIZA O JOB ---
    @Override
    protected RepeatStatus execute(JobParams parameters, long sequence) {
        int quantidade = marcarMensalidadesAtrasadasComponent.marcar();

        if (quantidade > 0) {
            logger.info("Financeiro: {} mensalidade(s) marcadas como ATRASADA.", quantidade);
        }

        return RepeatStatus.FINISHED;
    }
}