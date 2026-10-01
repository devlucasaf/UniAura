package erp.uniaura.modules.biblioteca.scheduler;

import cloudsupport.services.jobs.BaseSchedule;
import cloudsupport.services.jobs.Schedule;

import org.springframework.scheduling.annotation.Scheduled;

@Schedule
public class EmprestimoVencidoSchedule extends BaseSchedule {

    @Scheduled(cron = "0 0 3 * * *")
    public void executar() {
        runJob();
    }
}
