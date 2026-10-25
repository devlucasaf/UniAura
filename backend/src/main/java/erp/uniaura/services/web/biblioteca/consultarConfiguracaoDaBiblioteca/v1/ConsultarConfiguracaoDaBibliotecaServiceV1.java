package erp.uniaura.services.web.biblioteca.consultarConfiguracaoDaBiblioteca.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.ConfiguracaoBiblioteca;
import erp.uniaura.services.common.biblioteca.ObterConfiguracaoDaBibliotecaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultarConfiguracaoDaBibliotecaServiceV1 extends BaseService {

    private final ObterConfiguracaoDaBibliotecaComponent obterConfiguracaoDaBibliotecaComponent;

    // --- RETORNA OS PARÂMETROS ATUAIS DA BIBLIOTECA ---
    @Transactional(readOnly = true)
    public ConsultarConfiguracaoDaBibliotecaRetornoV1 consultar() {
        ConfiguracaoBiblioteca configuracao = obterConfiguracaoDaBibliotecaComponent.obter();

        ConsultarConfiguracaoDaBibliotecaRetornoV1 retorno = new ConsultarConfiguracaoDaBibliotecaRetornoV1();

        retorno.setPrazoEmprestimoAluno(configuracao.getPrazoEmprestimoAluno());
        retorno.setPrazoEmprestimoProfessor(configuracao.getPrazoEmprestimoProfessor());
        retorno.setMaximosEmprestimosSimultaneos(configuracao.getMaximosEmprestimosSimultaneos());
        retorno.setMaximasRenovacoes(configuracao.getMaximasRenovacoes());
        retorno.setValorMultaDia(configuracao.getValorMultaDia());

        return retorno;
    }
}
