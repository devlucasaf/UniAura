package erp.uniaura.modules.biblioteca.scheduler;

import cloudsupport.services.jobs.BaseJob;
import cloudsupport.services.jobs.Job;
import cloudsupport.services.jobs.JobParams;
import cloudsupport.services.jobs.RepeatStatus;

import erp.uniaura.modules.biblioteca.reserva.service.ReservaService;

import jakarta.inject.Inject;

@Job
public class ReservaExpiradaJob extends BaseJob {

    @Inject
    private ReservaService reservaService;

    @Override
    protected RepeatStatus execute(JobParams parameters, long sequence) {
        int qtd = reservaService.expirarReservasNaoRetiradas();
        if (qtd > 0) {
            logger.info("Biblioteca: {} reserva(s) expiradas por falta de retirada do exemplar.", qtd);
        }
        return RepeatStatus.FINISHED;
    }
}
