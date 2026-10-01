package erp.uniaura.modules.financeiro.scheduler;

import cloudsupport.services.jobs.BaseSchedule;
import cloudsupport.services.jobs.Schedule;

import org.springframework.scheduling.annotation.Scheduled;

@Schedule
public class MensalidadeAtrasadaSchedule extends BaseSchedule {

    @Scheduled(cron = "0 0 4 * * *")
    public void executar() {
        runJob();
    }
}
