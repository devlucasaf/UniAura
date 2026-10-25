package erp.uniaura.services.web.calendario.detalharEventoAcademico.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.EventoAcademico;
import erp.uniaura.services.common.calendario.BuscarEventoAcademicoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharEventoAcademicoServiceV1 extends BaseService {

    private final BuscarEventoAcademicoComponent buscarEventoAcademicoComponent;

    // --- DETALHA UM EVENTO DO CALENDÁRIO ACADÊMICO PELO UID ---
    @Transactional(readOnly = true)
    public DetalharEventoAcademicoRetornoV1 detalhar(DetalharEventoAcademicoParamsV1 params) {
        EventoAcademico evento = buscarEventoAcademicoComponent.buscarPorUid(params.getUid());

        DetalharEventoAcademicoRetornoV1 retorno = new DetalharEventoAcademicoRetornoV1();

        retorno.setUid(evento.getUid());
        retorno.setTitulo(evento.getTitulo());
        retorno.setDescricao(evento.getDescricao());
        retorno.setDataInicio(evento.getDataInicio());
        retorno.setDataFim(evento.getDataFim());
        retorno.setTipo(evento.getTipo());
        retorno.setPublico(evento.getPublico());
        retorno.setCriadoEm(evento.getCriadoEm());
        retorno.setAtualizadoEm(evento.getAtualizadoEm());

        return retorno;
    }
}
