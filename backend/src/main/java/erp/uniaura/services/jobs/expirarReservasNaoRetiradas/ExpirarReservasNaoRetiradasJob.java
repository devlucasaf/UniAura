package erp.uniaura.services.jobs.expirarReservasNaoRetiradas;

import cloudsupport.services.jobs.BaseJob;
import cloudsupport.services.jobs.Job;
import cloudsupport.services.jobs.JobParams;
import cloudsupport.services.jobs.RepeatStatus;

import erp.uniaura.services.common.biblioteca.ExpirarReservasNaoRetiradasComponent;

import jakarta.inject.Inject;

@Job
public class ExpirarReservasNaoRetiradasJob extends BaseJob {

    @Inject
    private ExpirarReservasNaoRetiradasComponent expirarReservasNaoRetiradasComponent;

    // --- EXPIRA RESERVAS NÃO RETIRADAS E FINALIZA O JOB ---
    @Override
    protected RepeatStatus execute(JobParams parameters, long sequence) {
        int quantidade = expirarReservasNaoRetiradasComponent.expirar();

        if (quantidade > 0) {
            logger.info("Biblioteca: {} reserva(s) expiradas por falta de retirada do exemplar.", quantidade);
        }

        return RepeatStatus.FINISHED;
    }
}
