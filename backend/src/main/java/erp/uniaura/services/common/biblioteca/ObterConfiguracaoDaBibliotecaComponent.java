package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.config.CacheConfig;
import erp.uniaura.persistence.entities.ConfiguracaoBiblioteca;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ObterConfiguracaoDaBibliotecaComponent extends BaseComponent {

    // --- A ÚNICA LINHA DE CONFIGURAÇÃO, LIDA EM TODO EMPRÉSTIMO E RENOVAÇÃO ---
    @Transactional(readOnly = true)
    @Cacheable(CacheConfig.CACHE_CONFIGURACAO_BIBLIOTECA)
    public ConfiguracaoBiblioteca obter() {
        List<ConfiguracaoBiblioteca> configuracoes = em()
                .createQuery("select c from ConfiguracaoBiblioteca c", ConfiguracaoBiblioteca.class)
                .setMaxResults(1)
                .getResultList();

        if (configuracoes.isEmpty()) {
            throw new BusinessException("Configuração da biblioteca não inicializada.");
        }

        return configuracoes.get(0);
    }
}
