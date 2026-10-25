package erp.uniaura.services.jobs.marcarEmprestimosVencidos;

import cloudsupport.services.jobs.BaseSchedule;
import cloudsupport.services.jobs.Schedule;

import org.springframework.scheduling.annotation.Scheduled;

@Schedule
public class MarcarEmprestimosVencidosSchedule extends BaseSchedule {

    // --- TODO DIA ÀS 3H ---
    @Scheduled(cron = "0 0 3 * * *")
    public void executar() {
        runJob();
    }
}
