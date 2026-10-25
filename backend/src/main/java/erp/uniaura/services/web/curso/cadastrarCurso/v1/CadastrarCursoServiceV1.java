package erp.uniaura.services.web.curso.cadastrarCurso.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Curso;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarCursoServiceV1 extends BaseService {

    // --- CADASTRA UM NOVO CURSO ---
    @Transactional
    public Uid cadastrar(CadastrarCursoParamsV1 params) {
        Curso curso = new Curso();

        curso.setNome(params.getNome());
        curso.setDescricao(params.getDescricao());
        curso.setNivel(params.getNivel());
        curso.setDuracaoSemestres(params.getDuracaoSemestres());
        curso.setCargaHorariaTotal(params.getCargaHorariaTotal());
        curso.setAtivo(params.getAtivo() == null ? Boolean.TRUE : params.getAtivo());
        curso.persist();

        return new Uid(curso.getUid());
    }
}
