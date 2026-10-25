package erp.uniaura.services.web.biblioteca.pesquisarMeusEmprestimos.v1;

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
public class PesquisarMeusEmprestimosServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final CalcularDiasDeAtrasoComponent calcularDiasDeAtrasoComponent;
    private final BuscarValorDaMultaDoEmprestimoComponent buscarValorDaMultaDoEmprestimoComponent;

    // --- PESQUISA OS EMPRÉSTIMOS DO USUÁRIO LOGADO COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarMeusEmprestimosRetornoV1 pesquisar(Long usuarioId, PesquisarMeusEmprestimosParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        PageableQuery.Result<PesquisarMeusEmprestimosEmprestimoRetornoV1> resultado =
                PageableQuery.<Emprestimo, PesquisarMeusEmprestimosEmprestimoRetornoV1>builder()
                        .clazz(Emprestimo.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("usuario"), autenticado)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarMeusEmprestimosRetornoV1 retorno = new PesquisarMeusEmprestimosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O EMPRÉSTIMO NO ITEM DE RETORNO COM ATRASO E MULTA ---
    private PesquisarMeusEmprestimosEmprestimoRetornoV1 paraRetorno(Emprestimo emprestimo) {
        PesquisarMeusEmprestimosEmprestimoRetornoV1 item = new PesquisarMeusEmprestimosEmprestimoRetornoV1();

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
