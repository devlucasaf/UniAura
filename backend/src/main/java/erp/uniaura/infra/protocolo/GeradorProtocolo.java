package erp.uniaura.infra.protocolo;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Year;

@Component
public class GeradorProtocolo {

    private static final String ALFABETO = "23456789BCDFGHJKLMNPQRSTVWXZ";

    private static final int TAMANHO_SUFIXO = 8;

    private final SecureRandom random = new SecureRandom();

    // --- GERA UM PROTOCOLO NO FORMATO PREFIXO-ANO-SUFIXO ---
    public String gerar(String prefixo) {
        StringBuilder sufixo = new StringBuilder(TAMANHO_SUFIXO);
        for (int i = 0; i < TAMANHO_SUFIXO; i++) {
            sufixo.append(ALFABETO.charAt(random.nextInt(ALFABETO.length())));
        }
        return "%s-%d-%s".formatted(prefixo, Year.now().getValue(), sufixo);
    }
}
