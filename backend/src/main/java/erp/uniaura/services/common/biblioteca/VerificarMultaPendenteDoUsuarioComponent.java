package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.StatusMulta;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class VerificarMultaPendenteDoUsuarioComponent extends BaseComponent {

    // --- VERDADEIRO QUANDO O USUÁRIO TEM ALGUMA MULTA AINDA NÃO PAGA ---
    public boolean existe(Usuario usuario) {
        Long pendentes = em()
                .createQuery("select count(m) from Multa m where m.emprestimo.usuario = :usuario and m.status = :status", Long.class)
                .setParameter("usuario", usuario)
                .setParameter("status", StatusMulta.PENDENTE)
                .getSingleResult();

        return pendentes > 0;
    }
}
