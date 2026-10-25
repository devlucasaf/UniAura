package erp.uniaura.services.web.financeiro.detalharMensalidade.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.Pagamento;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.financeiro.BuscarMensalidadeComponent;
import erp.uniaura.services.common.financeiro.BuscarPagamentoDaMensalidadeComponent;
import erp.uniaura.services.common.financeiro.VerificarAcessoAMensalidadeComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetalharMensalidadeServiceV1 extends BaseService {

    private final BuscarMensalidadeComponent buscarMensalidadeComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarAcessoAMensalidadeComponent verificarAcessoAMensalidadeComponent;
    private final BuscarPagamentoDaMensalidadeComponent buscarPagamentoDaMensalidadeComponent;

    // --- DETALHA A MENSALIDADE E OS DADOS DO PAGAMENTO, SE HOUVER ---
    @Transactional(readOnly = true)
    public DetalharMensalidadeRetornoV1 detalhar(Long usuarioId, DetalharMensalidadeParamsV1 params) {
        Mensalidade mensalidade = buscarMensalidadeComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAcessoAMensalidadeComponent.validar(mensalidade, autenticado);

        DetalharMensalidadeRetornoV1 retorno = new DetalharMensalidadeRetornoV1();

        retorno.setUid(mensalidade.getUid());
        retorno.setAlunoUid(mensalidade.getAluno().getUid());
        retorno.setCompetencia(mensalidade.getCompetencia());
        retorno.setValor(mensalidade.getValor());
        retorno.setVencimento(mensalidade.getVencimento());
        retorno.setStatus(mensalidade.getStatus());
        retorno.setDescricao(mensalidade.getDescricao());
        retorno.setCriadoEm(mensalidade.getCriadoEm());
        retorno.setAtualizadoEm(mensalidade.getAtualizadoEm());

        if (mensalidade.getAluno().getUsuario() != null) {
            retorno.setAlunoNome(mensalidade.getAluno().getUsuario().getNome());
        }

        Optional<Pagamento> pagamento = buscarPagamentoDaMensalidadeComponent.buscar(mensalidade);

        if (pagamento.isPresent()) {
            retorno.setFormaPagamento(pagamento.get().getFormaPagamento());
            retorno.setValorPago(pagamento.get().getValorPago());
            retorno.setValorMulta(pagamento.get().getValorMulta());
            retorno.setCartaoFinal(pagamento.get().getCartaoFinal());
            retorno.setDataPagamento(pagamento.get().getDataPagamento());
        }

        return retorno;
    }
}
