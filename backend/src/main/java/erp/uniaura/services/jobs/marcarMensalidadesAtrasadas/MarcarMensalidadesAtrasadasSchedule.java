package erp.uniaura.services.jobs.marcarMensalidadesAtrasadas;

import cloudsupport.services.jobs.BaseSchedule;
import cloudsupport.services.jobs.Schedule;

import org.springframework.scheduling.annotation.Scheduled;

@Schedule
public class MarcarMensalidadesAtrasadasSchedule extends BaseSchedule {

    // --- TODO DIA ÀS 4H ---
    @Scheduled(cron = "0 0 4 * * *")
    public void executar() {
        runJob();
    }
}
