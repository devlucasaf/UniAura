package erp.uniaura.services.web.biblioteca.alterarConfiguracaoDaBiblioteca.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.config.CacheConfig;
import erp.uniaura.persistence.entities.ConfiguracaoBiblioteca;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlterarConfiguracaoDaBibliotecaServiceV1 extends BaseService {

    // --- LÊ A LINHA DIRETO DO BANCO E, AO GRAVAR, LIMPA O CACHE PARA A PRÓXIMA LEITURA ---
    @Transactional
    @CacheEvict(CacheConfig.CACHE_CONFIGURACAO_BIBLIOTECA)
    public void alterar(AlterarConfiguracaoDaBibliotecaParamsV1 params) {
        List<ConfiguracaoBiblioteca> configuracoes = em()
                .createQuery("select c from ConfiguracaoBiblioteca c", ConfiguracaoBiblioteca.class)
                .setMaxResults(1)
                .getResultList();

        if (configuracoes.isEmpty()) {
            throw new BusinessException("Configuração da biblioteca não inicializada.");
        }

        ConfiguracaoBiblioteca configuracao = configuracoes.get(0);

        configuracao.setPrazoEmprestimoAluno(params.getPrazoEmprestimoAluno());
        configuracao.setPrazoEmprestimoProfessor(params.getPrazoEmprestimoProfessor());
        configuracao.setMaximosEmprestimosSimultaneos(params.getMaximosEmprestimosSimultaneos());
        configuracao.setMaximasRenovacoes(params.getMaximasRenovacoes());
        configuracao.setValorMultaDia(params.getValorMultaDia());
        configuracao.merge();
    }
}
