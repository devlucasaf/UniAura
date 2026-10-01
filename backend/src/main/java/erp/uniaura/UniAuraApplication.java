package erp.uniaura;

import cloudsupport.configuration.EnableCloudsupport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableCloudsupport
public class UniAuraApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniAuraApplication.class, args);
    }

}

