package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.ConfiguracaoBiblioteca;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalcularPrazoDoEmprestimoComponent extends BaseComponent {

    private final ObterConfiguracaoDaBibliotecaComponent obterConfiguracaoDaBibliotecaComponent;

    // --- PRAZO EM DIAS ---
    public int calcular(Usuario usuario) {
        ConfiguracaoBiblioteca configuracao = obterConfiguracaoDaBibliotecaComponent.obter();

        if (usuario.getRole() == TipoUsuario.PROFESSOR) {
            return configuracao.getPrazoEmprestimoProfessor();
        }

        return configuracao.getPrazoEmprestimoAluno();
    }
}
