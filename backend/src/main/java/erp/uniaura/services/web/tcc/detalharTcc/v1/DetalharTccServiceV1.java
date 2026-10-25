package erp.uniaura.services.web.tcc.detalharTcc.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.services.common.tcc.BuscarTccComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharTccServiceV1 extends BaseService {

    private final BuscarTccComponent buscarTccComponent;

    @Transactional(readOnly = true)
    public DetalharTccRetornoV1 detalhar(DetalharTccParamsV1 params) {
        Tcc tcc = buscarTccComponent.buscarPorUid(params.getUid());

        DetalharTccRetornoV1 retorno = new DetalharTccRetornoV1();

        retorno.setUid(tcc.getUid());
        retorno.setAlunoUid(tcc.getAluno().getUid());
        retorno.setAlunoNome(tcc.getAluno().getUsuario().getNome());
        retorno.setTitulo(tcc.getTitulo());
        retorno.setProfessorOrientadorNome(tcc.getProfessorOrientador().getUsuario().getNome());

        if (tcc.getProfessorCoorientador() != null) {
            retorno.setProfessorCoorientadorNome(tcc.getProfessorCoorientador().getUsuario().getNome());
        }

        retorno.setPeriodoLetivo(tcc.getPeriodoLetivo());
        retorno.setStatus(tcc.getStatus());
        retorno.setNotaFinal(tcc.getNotaFinal());
        retorno.setDataDefesa(tcc.getDataDefesa());
        retorno.setCriadoEm(tcc.getCriadoEm());
        retorno.setAtualizadoEm(tcc.getAtualizadoEm());

        return retorno;
    }
}
