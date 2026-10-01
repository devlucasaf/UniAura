package erp.uniaura.modules.biblioteca.scheduler;

import cloudsupport.services.jobs.BaseSchedule;
import cloudsupport.services.jobs.Schedule;

import org.springframework.scheduling.annotation.Scheduled;

@Schedule
public class ReservaExpiradaSchedule extends BaseSchedule {

    @Scheduled(cron = "0 30 3 * * *")
    public void executar() {
        runJob();
    }
}
