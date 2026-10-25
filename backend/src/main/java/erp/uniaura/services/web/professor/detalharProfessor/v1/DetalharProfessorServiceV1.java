package erp.uniaura.services.web.professor.detalharProfessor.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharProfessorServiceV1 extends BaseService {

    private final BuscarProfessorComponent buscarProfessorComponent;

    // --- DETALHA O PROFESSOR E SEUS DADOS DE USUÁRIO ---
    @Transactional(readOnly = true)
    public DetalharProfessorRetornoV1 detalhar(DetalharProfessorParamsV1 params) {
        Professor professor = buscarProfessorComponent.buscarPorUid(params.getUid());
        Usuario usuario = professor.getUsuario();

        DetalharProfessorRetornoV1 retorno = new DetalharProfessorRetornoV1();

        retorno.setUid(professor.getUid());
        retorno.setId(professor.getId());
        retorno.setNome(usuario.getNome());
        retorno.setEmail(usuario.getEmail());
        retorno.setCpf(usuario.getCpf());
        retorno.setTelefone(usuario.getTelefone());
        retorno.setDataNascimento(usuario.getDataNascimento());
        retorno.setFormacao(professor.getFormacao());
        retorno.setAreaAtuacao(professor.getAreaAtuacao());
        retorno.setCargaHorariaSemanal(professor.getCargaHorariaSemanal());
        retorno.setDataAdmissao(professor.getDataAdmissao());
        retorno.setAtivo(professor.getAtivo());
        retorno.setCriadoEm(professor.getCriadoEm());
        retorno.setAtualizadoEm(professor.getAtualizadoEm());

        return retorno;
    }
}
