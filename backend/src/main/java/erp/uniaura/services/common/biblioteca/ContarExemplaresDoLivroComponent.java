package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Livro;
import erp.uniaura.persistence.entities.StatusExemplar;

import org.springframework.stereotype.Component;

@Component
public class ContarExemplaresDoLivroComponent extends BaseComponent {

    // --- TOTAL DE EXEMPLARES DO LIVRO E QUANTOS ESTÃO DISPONÍVEIS PARA EMPRÉSTIMO ---
    public ContagemDeExemplares contar(Livro livro) {
        Long total = em()
                .createQuery("select count(e) from Exemplar e where e.livro = :livro", Long.class)
                .setParameter("livro", livro)
                .getSingleResult();

        Long disponiveis = em()
                .createQuery("select count(e) from Exemplar e where e.livro = :livro and e.status = :status", Long.class)
                .setParameter("livro", livro)
                .setParameter("status", StatusExemplar.DISPONIVEL)
                .getSingleResult();

        return new ContagemDeExemplares(total, disponiveis);
    }
}
