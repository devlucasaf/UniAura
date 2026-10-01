package erp.uniaura.infra.config;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    public static final String CACHE_CURSOS = "cursos";
    public static final String CACHE_CONFIGURACAO_BIBLIOTECA = "configuracaoBiblioteca";

    // --- PERSONALIZA O CACHE MANAGER PADRÃO DO SPRING COM EXPIRAÇÃO CURTA (DADOS MUDAM RARO, MAS NÃO SÃO ESTÁTICOS) ---
    @Bean
    public CacheManagerCustomizer<CaffeineCacheManager> cacheManagerCustomizer() {
        return cacheManager -> cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        .maximumSize(500));
    }
}
