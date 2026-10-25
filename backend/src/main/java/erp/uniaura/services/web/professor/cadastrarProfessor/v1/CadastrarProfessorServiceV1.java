package erp.uniaura.services.web.professor.cadastrarProfessor.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.Uid;

import erp.uniaura.services.common.usuario.CriarUsuarioComponent;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarProfessorServiceV1 extends BaseService {
    private final CriarUsuarioComponent criarUsuarioComponent;

    // --- CRIA O USUÁRIO E O PROFESSOR VINCULADO ---
    @Transactional
    public Uid cadastrar(CadastrarProfessorParamsV1 params) {
        Usuario usuario = criarUsuarioComponent.criar(params.getNome(), params.getEmail(), params.getSenha(), params.getCpf(), params.getTelefone(), params.getDataNascimento(), true, TipoUsuario.PROFESSOR);

        Professor professor = new Professor();

        professor.setUsuario(usuario);
        professor.setFormacao(params.getFormacao());
        professor.setAreaAtuacao(params.getAreaAtuacao());
        professor.setCargaHorariaSemanal(params.getCargaHorariaSemanal());
        professor.setDataAdmissao(params.getDataAdmissao());
        professor.setAtivo(params.getAtivo() == null ? Boolean.TRUE : params.getAtivo());
        professor.persist();

        return new Uid(professor.getUid());
    }
}
