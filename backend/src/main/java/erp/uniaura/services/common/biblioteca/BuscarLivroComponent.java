package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Livro;

import org.springframework.stereotype.Component;

@Component
public class BuscarLivroComponent extends BaseComponent {

    // --- BUSCA LIVRO POR UID OU LANÇA EXCEÇÃO ---
    public Livro buscarPorUid(Long uid) {
        return findByUid(Livro.class, uid)
                .orElseThrow(() -> new BusinessException("Livro não encontrado."));
    }
}
