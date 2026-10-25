package erp.uniaura.services.web.professor.alterarProfessor.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarProfessorServiceV1 extends BaseService {

    private final BuscarProfessorComponent buscarProfessorComponent;

    // --- ALTERA OS DADOS DO PROFESSOR E DO USUÁRIO VINCULADO ---
    @Transactional
    public void alterar(AlterarProfessorParamsV1 params) {
        Professor professor = buscarProfessorComponent.buscarPorUid(params.getUid());
        Usuario usuario = professor.getUsuario();

        usuario.setNome(params.getNome());
        usuario.setEmail(params.getEmail());
        usuario.setCpf(params.getCpf());
        usuario.setTelefone(params.getTelefone());
        usuario.setDataNascimento(params.getDataNascimento());
        usuario.merge();

        professor.setFormacao(params.getFormacao());
        professor.setAreaAtuacao(params.getAreaAtuacao());
        professor.setCargaHorariaSemanal(params.getCargaHorariaSemanal());
        professor.setDataAdmissao(params.getDataAdmissao());

        if (params.getAtivo() != null) {
            professor.setAtivo(params.getAtivo());
        }

        professor.merge();
    }
}
