package erp.uniaura.services.web.matricula.detalharMatricula.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Matricula;
import erp.uniaura.services.common.matricula.BuscarMatriculaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharMatriculaServiceV1 extends BaseService {

    private final BuscarMatriculaComponent buscarMatriculaComponent;

    // --- DETALHA A MATRÍCULA PELO UID ---
    @Transactional(readOnly = true)
    public DetalharMatriculaRetornoV1 detalhar(DetalharMatriculaParamsV1 params) {
        Matricula matricula = buscarMatriculaComponent.buscarPorUid(params.getUid());

        DetalharMatriculaRetornoV1 retorno = new DetalharMatriculaRetornoV1();

        retorno.setUid(matricula.getUid());
        retorno.setAlunoUid(matricula.getAluno().getUid());
        retorno.setAlunoNome(matricula.getAluno().getUsuario().getNome());
        retorno.setAlunoMatriculaRA(matricula.getAluno().getMatriculaRA());
        retorno.setTurmaUid(matricula.getTurma().getUid());
        retorno.setTurmaCodigo(matricula.getTurma().getCodigo());
        retorno.setTurmaPeriodoLetivo(matricula.getTurma().getPeriodoLetivo());
        retorno.setDataMatricula(matricula.getDataMatricula());
        retorno.setStatus(matricula.getStatus());
        retorno.setObservacoes(matricula.getObservacoes());

        if (matricula.getCriadaPor() != null) {
            retorno.setCriadaPorNome(matricula.getCriadaPor().getNome());
        }

        retorno.setCriadoEm(matricula.getCriadoEm());
        retorno.setAtualizadoEm(matricula.getAtualizadoEm());

        return retorno;
    }
}
