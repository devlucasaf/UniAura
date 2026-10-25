package erp.uniaura.services.common.aluno;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarAlunoComponent extends BaseComponent {

    // --- PELO UID, QUE É O IDENTIFICADOR PÚBLICO DAS FUNCIONALIDADES NOVAS ---
    public Aluno buscarPorUid(Long uid) {
        return findByUid(Aluno.class, uid)
                .orElseThrow(() -> new BusinessException("Aluno não encontrado."));
    }

    // --- PELO USUÁRIO LOGADO, QUANDO O PRÓPRIO ALUNO FAZ A OPERAÇÃO ---
    public Aluno buscarPorUsuarioId(Long usuarioId) {
        List<Aluno> encontrados = em()
                .createQuery("select a from Aluno a where a.usuario.id = :usuarioId", Aluno.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();

        if (encontrados.isEmpty()) {
            throw new BusinessException("O usuário autenticado não possui cadastro de aluno.");
        }

        return encontrados.get(0);
    }
}
