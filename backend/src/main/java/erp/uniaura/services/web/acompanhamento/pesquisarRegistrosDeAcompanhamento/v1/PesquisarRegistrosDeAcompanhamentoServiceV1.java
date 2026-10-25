package erp.uniaura.services.web.acompanhamento.pesquisarRegistrosDeAcompanhamento.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.AcompanhamentoEspecial;
import erp.uniaura.persistence.entities.RegistroDeAcompanhamento;
import erp.uniaura.services.common.acompanhamento.BuscarAcompanhamentoEspecialComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarRegistrosDeAcompanhamentoServiceV1 extends BaseService {

    private final BuscarAcompanhamentoEspecialComponent buscarAcompanhamentoEspecialComponent;

    // --- LISTA O HISTÓRICO DE UM ACOMPANHAMENTO DE FORMA PAGINADA, COM FILTRO OPCIONAL DE TIPO DE REGISTRO ---
    @Transactional(readOnly = true)
    public PesquisarRegistrosDeAcompanhamentoRetornoV1 pesquisar(PesquisarRegistrosDeAcompanhamentoParamsV1 params) {
        AcompanhamentoEspecial acompanhamento = buscarAcompanhamentoEspecialComponent.buscarPorUid(params.getAcompanhamentoUid());

        PageableQuery.Result<PesquisarRegistrosDeAcompanhamentoRegistroRetornoV1> resultado =
                PageableQuery.<RegistroDeAcompanhamento, PesquisarRegistrosDeAcompanhamentoRegistroRetornoV1>builder()
                        .clazz(RegistroDeAcompanhamento.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            condicoes.add(cb().equal(from.get("acompanhamento"), acompanhamento));

                            if (params.getTipo() != null) {
                                condicoes.add(cb().equal(from.get("tipo"), params.getTipo()));
                            }

                            criteria.where(condicoes.toArray(new Predicate[0]));
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarRegistrosDeAcompanhamentoRetornoV1 retorno = new PesquisarRegistrosDeAcompanhamentoRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A ENTIDADE NO ITEM DA LISTA ---
    private PesquisarRegistrosDeAcompanhamentoRegistroRetornoV1 paraRetorno(RegistroDeAcompanhamento registro) {
        PesquisarRegistrosDeAcompanhamentoRegistroRetornoV1 item = new PesquisarRegistrosDeAcompanhamentoRegistroRetornoV1();

        item.setUid(registro.getUid());
        item.setTipo(registro.getTipo());
        item.setDataRegistro(registro.getDataRegistro());
        item.setDescricao(registro.getDescricao());
        item.setAutor(registro.getAutor().getNome());

        return item;
    }
}
