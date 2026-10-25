package erp.uniaura.services.web.curso.alterarCurso.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Curso;
import erp.uniaura.services.common.curso.BuscarCursoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarCursoServiceV1 extends BaseService {

    private final BuscarCursoComponent buscarCursoComponent;

    // --- ALTERA OS DADOS DO CURSO ---
    @Transactional
    public void alterar(AlterarCursoParamsV1 params) {
        Curso curso = buscarCursoComponent.buscarPorUid(params.getUid());

        curso.setNome(params.getNome());
        curso.setDescricao(params.getDescricao());
        curso.setNivel(params.getNivel());
        curso.setDuracaoSemestres(params.getDuracaoSemestres());
        curso.setCargaHorariaTotal(params.getCargaHorariaTotal());

        if (params.getAtivo() != null) {
            curso.setAtivo(params.getAtivo());
        }

        curso.merge();
    }
}
