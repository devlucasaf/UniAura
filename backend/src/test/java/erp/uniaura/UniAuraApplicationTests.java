package erp.uniaura;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// --- WEBENVIRONMENT REAL (NAO MOCK): O AppEventReadyListener DO CLOUDSUPPORT CALCULA
// --- O NUMERO DE ENDPOINTS DO ACTUATOR NO EVENTO DE SERVIDOR INICIADO, QUE SO DISPARA
// --- COM UM SERVIDOR EMBUTIDO DE VERDADE. TAMBEM EXERCITA A CADEIA DE SEGURANCA JWT.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UniAuraApplicationTests {

    @Test
    void contextLoads() {
    }

}

