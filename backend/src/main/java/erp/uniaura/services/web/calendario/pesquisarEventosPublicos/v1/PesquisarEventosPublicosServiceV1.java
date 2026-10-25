package erp.uniaura.services.web.calendario.pesquisarEventosPublicos.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.EventoAcademico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PesquisarEventosPublicosServiceV1 extends BaseService {

    // --- LISTA OS EVENTOS PÚBLICOS ORDENADOS POR DATA DE INÍCIO ---
    @Transactional(readOnly = true)
    public PesquisarEventosPublicosRetornoV1 pesquisar() {
        List<EventoAcademico> eventos = em()
                .createQuery("select e from EventoAcademico e where e.publico = true order by e.dataInicio asc", EventoAcademico.class)
                .getResultList();

        PesquisarEventosPublicosRetornoV1 retorno = new PesquisarEventosPublicosRetornoV1();
        retorno.setLista(eventos.stream().map(this::paraRetorno).toList());

        return retorno;
    }

    // --- CONVERTE O EVENTO NO ITEM DE RETORNO PÚBLICO ---
    private PesquisarEventosPublicosEventoRetornoV1 paraRetorno(EventoAcademico evento) {
        PesquisarEventosPublicosEventoRetornoV1 item = new PesquisarEventosPublicosEventoRetornoV1();

        item.setUid(evento.getUid());
        item.setTitulo(evento.getTitulo());
        item.setDescricao(evento.getDescricao());
        item.setDataInicio(evento.getDataInicio());
        item.setDataFim(evento.getDataFim());
        item.setTipo(evento.getTipo());

        return item;
    }
}
