package erp.uniaura.services.common.professor;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Professor;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BuscarProfessorComponent extends BaseComponent {

    // --- PELO UID, QUE É O IDENTIFICADOR PÚBLICO DAS FUNCIONALIDADES NOVAS ---
    public Professor buscarPorUid(Long uid) {
        return findByUid(Professor.class, uid)
                .orElseThrow(() -> new BusinessException("Professor não encontrado."));
    }

    // --- USO TRANSITÓRIO DOS SERVICES PELO ID SEQUENCIAL ---
    public Professor buscarPorId(Long id) {
        return findById(Professor.class, id)
                .orElseThrow(() -> new BusinessException("Professor não encontrado."));
    }

    // --- O PROFESSOR DO USUÁRIO LOGADO, SE HOUVER; QUEM CHAMA DECIDE A MENSAGEM QUANDO NÃO HÁ ---
    public Optional<Professor> buscarPorUsuarioId(Long usuarioId) {
        return em()
                .createQuery("select p from Professor p where p.usuario.id = :usuarioId", Professor.class)
                .setParameter("usuarioId", usuarioId)
                .getResultStream()
                .findFirst();
    }
}
