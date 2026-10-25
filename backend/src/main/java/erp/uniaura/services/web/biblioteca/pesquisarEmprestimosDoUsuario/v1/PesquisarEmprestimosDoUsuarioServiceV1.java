package erp.uniaura.services.web.biblioteca.pesquisarEmprestimosDoUsuario.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Emprestimo;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.biblioteca.BuscarValorDaMultaDoEmprestimoComponent;
import erp.uniaura.services.common.biblioteca.CalcularDiasDeAtrasoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarEmprestimosDoUsuarioServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final CalcularDiasDeAtrasoComponent calcularDiasDeAtrasoComponent;
    private final BuscarValorDaMultaDoEmprestimoComponent buscarValorDaMultaDoEmprestimoComponent;

    // --- PESQUISA OS EMPRÉSTIMOS DE UM USUÁRIO COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarEmprestimosDoUsuarioRetornoV1 pesquisar(PesquisarEmprestimosDoUsuarioParamsV1 params) {
        Usuario usuario = buscarUsuarioComponent.buscarPorUid(params.getUsuarioUid());

        PageableQuery.Result<PesquisarEmprestimosDoUsuarioEmprestimoRetornoV1> resultado =
                PageableQuery.<Emprestimo, PesquisarEmprestimosDoUsuarioEmprestimoRetornoV1>builder()
                        .clazz(Emprestimo.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("usuario"), usuario)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarEmprestimosDoUsuarioRetornoV1 retorno = new PesquisarEmprestimosDoUsuarioRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O EMPRÉSTIMO NO ITEM DE RETORNO COM ATRASO E MULTA ---
    private PesquisarEmprestimosDoUsuarioEmprestimoRetornoV1 paraRetorno(Emprestimo emprestimo) {
        PesquisarEmprestimosDoUsuarioEmprestimoRetornoV1 item = new PesquisarEmprestimosDoUsuarioEmprestimoRetornoV1();

        item.setUid(emprestimo.getUid());
        item.setLivroTitulo(emprestimo.getExemplar().getLivro().getTitulo());
        item.setExemplarCodigoBarras(emprestimo.getExemplar().getCodigoBarras());
        item.setDataEmprestimo(emprestimo.getDataEmprestimo());
        item.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());
        item.setDataDevolucaoEfetiva(emprestimo.getDataDevolucaoEfetiva());
        item.setRenovacoes(emprestimo.getRenovacoes());
        item.setStatus(emprestimo.getStatus());
        item.setDiasAtraso(calcularDiasDeAtrasoComponent.calcularDoEmprestimo(emprestimo));
        item.setValorMulta(buscarValorDaMultaDoEmprestimoComponent.buscar(emprestimo));

        return item;
    }
}
