package erp.uniaura.services.web.aluno.removerAluno.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.auditoria.RegistrarAuditoriaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverAlunoServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final RegistrarAuditoriaComponent registrarAuditoriaComponent;

    // --- A EXCLUSÃO FICA NA TRILHA DE AUDITORIA COM O NOME DO ALUNO REMOVIDO ---
    @Transactional
    public void remover(Long usuarioId, RemoverAlunoParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        Long id = aluno.getId();
        String nome = null;

        if (aluno.getUsuario() != null) {
            nome = aluno.getUsuario().getNome();
        }

        aluno.remove();

        registrarAuditoriaComponent.registrar(autenticado, "EXCLUSAO", "Aluno", id, "Aluno removido: " + nome);
    }
}
