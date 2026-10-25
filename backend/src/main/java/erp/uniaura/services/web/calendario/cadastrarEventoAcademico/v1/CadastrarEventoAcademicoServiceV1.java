package erp.uniaura.services.web.calendario.cadastrarEventoAcademico.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.EventoAcademico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarEventoAcademicoServiceV1 extends BaseService {

    // --- CADASTRA UM NOVO EVENTO NO CALENDÁRIO ACADÊMICO ---
    @Transactional
    public Uid cadastrar(CadastrarEventoAcademicoParamsV1 params) {
        EventoAcademico evento = new EventoAcademico();

        evento.setTitulo(params.getTitulo());
        evento.setDescricao(params.getDescricao());
        evento.setDataInicio(params.getDataInicio());
        evento.setDataFim(params.getDataFim());
        evento.setTipo(params.getTipo());
        evento.setPublico(Boolean.TRUE.equals(params.getPublico()));
        evento.persist();

        return new Uid(evento.getUid());
    }
}
