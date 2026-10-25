package erp.uniaura.services.web.biblioteca.pesquisarFilaDeReservasDoLivro.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Livro;
import erp.uniaura.persistence.entities.Reserva;
import erp.uniaura.persistence.entities.StatusReserva;
import erp.uniaura.services.common.biblioteca.BuscarLivroComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarFilaDeReservasDoLivroServiceV1 extends BaseService {

    private final BuscarLivroComponent buscarLivroComponent;

    // --- PESQUISA A FILA DE RESERVAS AGUARDANDO DE UM LIVRO COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarFilaDeReservasDoLivroRetornoV1 pesquisar(PesquisarFilaDeReservasDoLivroParamsV1 params) {
        Livro livro = buscarLivroComponent.buscarPorUid(params.getLivroUid());

        PageableQuery.Result<PesquisarFilaDeReservasDoLivroReservaRetornoV1> resultado =
                PageableQuery.<Reserva, PesquisarFilaDeReservasDoLivroReservaRetornoV1>builder()
                        .clazz(Reserva.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order("+posicaoFila")
                        .predicate((criteria, from) -> {
                            Predicate doLivro = cb().equal(from.get("livro"), livro);
                            Predicate aguardando = cb().equal(from.get("status"), StatusReserva.AGUARDANDO);
                            criteria.where(doLivro, aguardando);
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarFilaDeReservasDoLivroRetornoV1 retorno = new PesquisarFilaDeReservasDoLivroRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A RESERVA NO ITEM DE RETORNO DA FILA ---
    private PesquisarFilaDeReservasDoLivroReservaRetornoV1 paraRetorno(Reserva reserva) {
        PesquisarFilaDeReservasDoLivroReservaRetornoV1 item = new PesquisarFilaDeReservasDoLivroReservaRetornoV1();

        item.setUid(reserva.getUid());
        item.setPosicaoFila(reserva.getPosicaoFila());
        item.setUsuarioNome(reserva.getUsuario().getNome());
        item.setDataReserva(reserva.getDataReserva());
        item.setPrazoRetirada(reserva.getPrazoRetirada());
        item.setStatus(reserva.getStatus());

        return item;
    }
}
