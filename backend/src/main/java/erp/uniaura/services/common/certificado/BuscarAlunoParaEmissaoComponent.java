package erp.uniaura.services.common.certificado;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuscarAlunoParaEmissaoComponent extends BaseComponent {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- O ALUNO SÓ EMITE OS PRÓPRIOS DOCUMENTOS; A EQUIPE EMITE DE QUALQUER ALUNO ---
    public Aluno buscar(Long usuarioId, Long alunoUid) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(alunoUid);
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        if (autenticado.getRole() == TipoUsuario.ALUNO && !aluno.getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você só pode emitir documentos do seu próprio cadastro.");
        }

        return aluno;
    }
}
