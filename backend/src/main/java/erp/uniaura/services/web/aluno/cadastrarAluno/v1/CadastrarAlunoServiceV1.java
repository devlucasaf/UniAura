package erp.uniaura.services.web.aluno.cadastrarAluno.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoPorMatriculaComponent;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;
import erp.uniaura.services.common.usuario.CriarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarAlunoServiceV1 extends BaseService {

    private final BuscarAlunoPorMatriculaComponent buscarAlunoPorMatriculaComponent;
    private final BuscarTurmaComponent buscarTurmaComponent;
    private final CriarUsuarioComponent criarUsuarioComponent;

    // --- CRIA O USUÁRIO DE ACESSO JUNTO COM O ALUNO ---
    @Transactional
    public Uid cadastrar(CadastrarAlunoParamsV1 params) {
        if (buscarAlunoPorMatriculaComponent.buscar(params.getMatriculaRA()).isPresent()) {
            throw new BusinessException("Já existe um aluno cadastrado com a matrícula: " + params.getMatriculaRA());
        }

        Usuario usuario = criarUsuarioComponent.criar(params.getNome(), params.getEmail(), params.getSenha(), params.getCpf(),
                params.getTelefone(), params.getDataNascimento(), true, TipoUsuario.ALUNO);

        Aluno aluno = new Aluno();

        aluno.setUsuario(usuario);
        aluno.setMatriculaRA(params.getMatriculaRA());
        aluno.setDataIngresso(params.getDataIngresso());
        aluno.setStatus(params.getStatus());
        aluno.setObservacoes(params.getObservacoes());

        if (params.getTurmaAtualUid() != null) {
            aluno.setTurmaAtualId(buscarTurmaComponent.buscarPorUid(params.getTurmaAtualUid()).getId());
        }

        aluno.persist();

        return new Uid(aluno.getUid());
    }
}
