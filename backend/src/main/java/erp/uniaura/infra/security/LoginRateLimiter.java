package erp.uniaura.infra.security;

import erp.uniaura.exception.BusinessException;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginRateLimiter {

    private static final int MAX_TENTATIVAS = 5;
    private static final Duration JANELA_BLOQUEIO = Duration.ofMinutes(15);

    private final Map<String, Tentativas> tentativasPorEmail = new ConcurrentHashMap<>();

    // --- BLOQUEIA O LOGIN SE O E-MAIL JÁ ATINGIU O LIMITE DE TENTATIVAS FALHAS NA JANELA ATUAL ---
    public void verificarBloqueio(String email) {
        Tentativas tentativas = tentativasPorEmail.get(normalizar(email));
        if (tentativas == null) {
            return;
        }

        if (tentativas.quantidade() >= MAX_TENTATIVAS && Instant.now().isBefore(tentativas.expiraEm())) {
            throw new BusinessException("Muitas tentativas de login falhas. Tente novamente em alguns minutos.");
        }
    }

    // --- REGISTRA UMA TENTATIVA DE LOGIN FALHA, ACUMULANDO NA JANELA ATUAL ---
    public void registrarFalha(String email) {
        tentativasPorEmail.compute(normalizar(email), (chave, atual) -> {
            if (atual == null || !Instant.now().isBefore(atual.expiraEm())) {
                return new Tentativas(1, Instant.now().plus(JANELA_BLOQUEIO));
            }
            return new Tentativas(atual.quantidade() + 1, atual.expiraEm());
        });
    }

    // --- LIMPA O HISTÓRICO DE TENTATIVAS APÓS UM LOGIN BEM-SUCEDIDO ---
    public void registrarSucesso(String email) {
        tentativasPorEmail.remove(normalizar(email));
    }

    // --- NORMALIZA O E-MAIL PARA EVITAR BYPASS POR DIFERENÇA DE CAIXA ---
    private String normalizar(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }

    private record Tentativas(int quantidade, Instant expiraEm) {
    }
}
