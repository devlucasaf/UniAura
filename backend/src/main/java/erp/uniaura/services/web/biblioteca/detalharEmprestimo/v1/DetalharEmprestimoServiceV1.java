package erp.uniaura.services.web.biblioteca.detalharEmprestimo.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Emprestimo;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.biblioteca.BuscarEmprestimoComponent;
import erp.uniaura.services.common.biblioteca.BuscarValorDaMultaDoEmprestimoComponent;
import erp.uniaura.services.common.biblioteca.CalcularDiasDeAtrasoComponent;
import erp.uniaura.services.common.biblioteca.VerificarAcessoAoEmprestimoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharEmprestimoServiceV1 extends BaseService {

    private final BuscarEmprestimoComponent buscarEmprestimoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarAcessoAoEmprestimoComponent verificarAcessoAoEmprestimoComponent;
    private final CalcularDiasDeAtrasoComponent calcularDiasDeAtrasoComponent;
    private final BuscarValorDaMultaDoEmprestimoComponent buscarValorDaMultaDoEmprestimoComponent;

    // --- DETALHA O EMPRÉSTIMO COM DIAS DE ATRASO E VALOR DA MULTA ---
    @Transactional(readOnly = true)
    public DetalharEmprestimoRetornoV1 detalhar(Long usuarioId, DetalharEmprestimoParamsV1 params) {
        Emprestimo emprestimo = buscarEmprestimoComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAcessoAoEmprestimoComponent.validar(emprestimo, autenticado);

        DetalharEmprestimoRetornoV1 retorno = new DetalharEmprestimoRetornoV1();

        retorno.setUid(emprestimo.getUid());
        retorno.setLivroTitulo(emprestimo.getExemplar().getLivro().getTitulo());
        retorno.setExemplarCodigoBarras(emprestimo.getExemplar().getCodigoBarras());
        retorno.setUsuarioNome(emprestimo.getUsuario().getNome());
        retorno.setDataEmprestimo(emprestimo.getDataEmprestimo());
        retorno.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());
        retorno.setDataDevolucaoEfetiva(emprestimo.getDataDevolucaoEfetiva());
        retorno.setRenovacoes(emprestimo.getRenovacoes());
        retorno.setStatus(emprestimo.getStatus());
        retorno.setDiasAtraso(calcularDiasDeAtrasoComponent.calcularDoEmprestimo(emprestimo));
        retorno.setValorMulta(buscarValorDaMultaDoEmprestimoComponent.buscar(emprestimo));

        return retorno;
    }
}
