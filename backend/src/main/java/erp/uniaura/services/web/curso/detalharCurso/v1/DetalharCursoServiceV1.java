package erp.uniaura.services.web.curso.detalharCurso.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Curso;
import erp.uniaura.services.common.curso.BuscarCursoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharCursoServiceV1 extends BaseService {

    private final BuscarCursoComponent buscarCursoComponent;

    // --- DETALHA UM CURSO PELO UID ---
    @Transactional(readOnly = true)
    public DetalharCursoRetornoV1 detalhar(DetalharCursoParamsV1 params) {
        Curso curso = buscarCursoComponent.buscarPorUid(params.getUid());

        DetalharCursoRetornoV1 retorno = new DetalharCursoRetornoV1();

        retorno.setUid(curso.getUid());
        retorno.setId(curso.getId());
        retorno.setNome(curso.getNome());
        retorno.setDescricao(curso.getDescricao());
        retorno.setNivel(curso.getNivel());
        retorno.setDuracaoSemestres(curso.getDuracaoSemestres());
        retorno.setCargaHorariaTotal(curso.getCargaHorariaTotal());
        retorno.setAtivo(curso.getAtivo());
        retorno.setCriadoEm(curso.getCriadoEm());
        retorno.setAtualizadoEm(curso.getAtualizadoEm());
        return retorno;
    }
}
