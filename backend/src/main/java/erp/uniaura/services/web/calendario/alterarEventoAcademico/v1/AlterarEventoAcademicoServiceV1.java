package erp.uniaura.services.web.calendario.alterarEventoAcademico.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.EventoAcademico;
import erp.uniaura.services.common.calendario.BuscarEventoAcademicoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarEventoAcademicoServiceV1 extends BaseService {

    private final BuscarEventoAcademicoComponent buscarEventoAcademicoComponent;

    // --- ALTERA OS DADOS DO EVENTO ACADÊMICO ---
    @Transactional
    public void alterar(AlterarEventoAcademicoParamsV1 params) {
        EventoAcademico evento = buscarEventoAcademicoComponent.buscarPorUid(params.getUid());

        evento.setTitulo(params.getTitulo());
        evento.setDescricao(params.getDescricao());
        evento.setDataInicio(params.getDataInicio());
        evento.setDataFim(params.getDataFim());
        evento.setTipo(params.getTipo());
        evento.setPublico(Boolean.TRUE.equals(params.getPublico()));
        evento.merge();
    }
}
