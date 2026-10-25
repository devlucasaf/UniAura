package erp.uniaura.services.web.aluno.alterarAluno.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.aluno.BuscarAlunoPorMatriculaComponent;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;
import erp.uniaura.services.common.usuario.ConsultarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarAlunoServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarAlunoPorMatriculaComponent buscarAlunoPorMatriculaComponent;
    private final BuscarTurmaComponent buscarTurmaComponent;
    private final ConsultarUsuarioComponent consultarUsuarioComponent;

    // --- ATUALIZA OS DADOS ACADÊMICOS E OS DO USUÁRIO VINCULADO ---
    @Transactional
    public void alterar(AlterarAlunoParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getUid());
        Usuario usuario = aluno.getUsuario();

        if (!aluno.getMatriculaRA().equalsIgnoreCase(params.getMatriculaRA())
                && buscarAlunoPorMatriculaComponent.buscar(params.getMatriculaRA()).isPresent()) {
            throw new BusinessException("Já existe um aluno com a matrícula: " + params.getMatriculaRA());
        }

        if (!usuario.getEmail().equalsIgnoreCase(params.getEmail())
                && consultarUsuarioComponent.porEmail(params.getEmail()).isPresent()) {
            throw new BusinessException("Já existe um usuário cadastrado com o e-mail: " + params.getEmail());
        }

        usuario.setNome(params.getNome());
        usuario.setEmail(params.getEmail());
        usuario.setCpf(params.getCpf());
        usuario.setTelefone(params.getTelefone());
        usuario.setDataNascimento(params.getDataNascimento());
        usuario.merge();

        aluno.setMatriculaRA(params.getMatriculaRA());
        aluno.setDataIngresso(params.getDataIngresso());
        aluno.setStatus(params.getStatus());
        aluno.setObservacoes(params.getObservacoes());

        if (params.getTurmaAtualUid() == null) {
            aluno.setTurmaAtualId(null);
        } else {
            aluno.setTurmaAtualId(buscarTurmaComponent.buscarPorUid(params.getTurmaAtualUid()).getId());
        }

        aluno.merge();
    }
}
