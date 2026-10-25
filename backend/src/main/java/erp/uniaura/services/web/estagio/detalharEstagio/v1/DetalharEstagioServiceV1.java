package erp.uniaura.services.web.estagio.detalharEstagio.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Estagio;
import erp.uniaura.services.common.estagio.BuscarEstagioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharEstagioServiceV1 extends BaseService {

    private final BuscarEstagioComponent buscarEstagioComponent;

    // --- DETALHA O ESTÁGIO PELO UID ---
    @Transactional(readOnly = true)
    public DetalharEstagioRetornoV1 detalhar(DetalharEstagioParamsV1 params) {
        Estagio estagio = buscarEstagioComponent.buscarPorUid(params.getUid());

        DetalharEstagioRetornoV1 retorno = new DetalharEstagioRetornoV1();

        retorno.setUid(estagio.getUid());
        retorno.setAlunoUid(estagio.getAluno().getUid());
        retorno.setAlunoNome(estagio.getAluno().getUsuario().getNome());
        retorno.setEmpresaConcedente(estagio.getEmpresaConcedente());
        retorno.setSupervisorEmpresa(estagio.getSupervisorEmpresa());

        if (estagio.getProfessorOrientador() != null) {
            retorno.setProfessorOrientadorNome(estagio.getProfessorOrientador().getUsuario().getNome());
        }

        retorno.setDataInicio(estagio.getDataInicio());
        retorno.setDataFimPrevista(estagio.getDataFimPrevista());
        retorno.setDataFimEfetiva(estagio.getDataFimEfetiva());
        retorno.setCargaHorariaTotal(estagio.getCargaHorariaTotal());
        retorno.setCargaHorariaCumprida(estagio.getCargaHorariaCumprida());
        retorno.setStatus(estagio.getStatus());
        retorno.setTermoCompromissoUrl(estagio.getTermoCompromissoUrl());
        retorno.setCriadoEm(estagio.getCriadoEm());
        retorno.setAtualizadoEm(estagio.getAtualizadoEm());

        return retorno;
    }
}
