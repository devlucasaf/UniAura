package erp.uniaura.services.common.usuario;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ConsultarUsuarioComponent extends BaseComponent {

    // --- PELO id SEQUENCIAL, SEM FALHAR QUANDO NÃO EXISTE ---
    public Optional<Usuario> porId(Long id) {
        return findById(Usuario.class, id);
    }

    // --- PELO E-MAIL, QUE É ÚNICO; SEM DIFERENCIAR MAIÚSCULAS ---
    public Optional<Usuario> porEmail(String email) {
        List<Usuario> encontrados = em()
                .createQuery("select u from Usuario u where lower(u.email) = :email", Usuario.class)
                .setParameter("email", email.toLowerCase())
                .getResultList();

        if (encontrados.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(encontrados.get(0));
    }
}
