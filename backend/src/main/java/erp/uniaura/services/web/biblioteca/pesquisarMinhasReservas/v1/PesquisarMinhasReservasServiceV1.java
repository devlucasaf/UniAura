package erp.uniaura.services.web.biblioteca.pesquisarMinhasReservas.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Reserva;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarMinhasReservasServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- PESQUISA AS RESERVAS DO USUÁRIO LOGADO COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarMinhasReservasRetornoV1 pesquisar(Long usuarioId, PesquisarMinhasReservasParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        PageableQuery.Result<PesquisarMinhasReservasReservaRetornoV1> resultado =
                PageableQuery.<Reserva, PesquisarMinhasReservasReservaRetornoV1>builder()
                        .clazz(Reserva.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("usuario"), autenticado)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarMinhasReservasRetornoV1 retorno = new PesquisarMinhasReservasRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A RESERVA NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarMinhasReservasReservaRetornoV1 paraRetorno(Reserva reserva) {
        PesquisarMinhasReservasReservaRetornoV1 item = new PesquisarMinhasReservasReservaRetornoV1();

        item.setUid(reserva.getUid());
        item.setLivroTitulo(reserva.getLivro().getTitulo());
        item.setDataReserva(reserva.getDataReserva());
        item.setPrazoRetirada(reserva.getPrazoRetirada());
        item.setStatus(reserva.getStatus());
        item.setPosicaoFila(reserva.getPosicaoFila());

        return item;
    }
}
