package erp.uniaura.services.jobs.expirarReservasNaoRetiradas;

import cloudsupport.services.jobs.BaseSchedule;
import cloudsupport.services.jobs.Schedule;

import org.springframework.scheduling.annotation.Scheduled;

@Schedule
public class ExpirarReservasNaoRetiradasSchedule extends BaseSchedule {

    // --- TODO DIA ÀS 3H30 ---
    @Scheduled(cron = "0 30 3 * * *")
    public void executar() {
        runJob();
    }
}
