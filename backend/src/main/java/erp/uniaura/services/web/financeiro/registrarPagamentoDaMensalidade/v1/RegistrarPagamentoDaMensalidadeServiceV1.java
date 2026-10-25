package erp.uniaura.services.web.financeiro.registrarPagamentoDaMensalidade.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.services.common.auditoria.RegistrarAuditoriaComponent;
import erp.uniaura.persistence.entities.FormaPagamento;
import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.Pagamento;
import erp.uniaura.persistence.entities.StatusMensalidade;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.financeiro.BuscarMensalidadeComponent;
import erp.uniaura.services.common.financeiro.CalcularMultaDaMensalidadeComponent;
import erp.uniaura.services.common.financeiro.VerificarAcessoAMensalidadeComponent;
import erp.uniaura.services.common.financeiro.VerificarMensalidadeEmAbertoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RegistrarPagamentoDaMensalidadeServiceV1 extends BaseService {

    private static final int DIGITOS_VISIVEIS_DO_CARTAO = 4;

    private final BuscarMensalidadeComponent buscarMensalidadeComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarAcessoAMensalidadeComponent verificarAcessoAMensalidadeComponent;
    private final VerificarMensalidadeEmAbertoComponent verificarMensalidadeEmAbertoComponent;
    private final CalcularMultaDaMensalidadeComponent calcularMultaDaMensalidadeComponent;
    private final RegistrarAuditoriaComponent registrarAuditoriaComponent;

    // --- REGISTRA O PAGAMENTO, MARCA A MENSALIDADE COMO PAGA E GRAVA A AUDITORIA ---
    @Transactional
    public void registrar(Long usuarioId, RegistrarPagamentoDaMensalidadeParamsV1 params) {
        Mensalidade mensalidade = buscarMensalidadeComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAcessoAMensalidadeComponent.validar(mensalidade, autenticado);
        verificarMensalidadeEmAbertoComponent.validar(mensalidade);

        if (params.getFormaPagamento() == FormaPagamento.CARTAO
                && (params.getCartaoNumero() == null || params.getCartaoValidade() == null || params.getCartaoCvv() == null)) {
            throw new BusinessException("Para pagamento com cartão, informe número, validade e CVV (dados fictícios).");
        }

        BigDecimal valorMulta = calcularMultaDaMensalidadeComponent.calcular(mensalidade);

        Pagamento pagamento = new Pagamento();

        pagamento.setMensalidade(mensalidade);
        pagamento.setFormaPagamento(params.getFormaPagamento());
        pagamento.setValorPago(mensalidade.getValor().add(valorMulta));
        pagamento.setValorMulta(valorMulta);
        pagamento.setCartaoFinal(ultimosDigitos(params.getCartaoNumero()));
        pagamento.persist();

        mensalidade.setStatus(StatusMensalidade.PAGA);
        mensalidade.merge();

        registrarAuditoriaComponent.registrar(autenticado, "PAGAMENTO_REGISTRADO", "Mensalidade", mensalidade.getId(),
                "Competência %s paga via %s".formatted(mensalidade.getCompetencia(), params.getFormaPagamento()));
    }

    // --- GUARDA SÓ OS ÚLTIMOS 4 DÍGITOS DO CARTÃO; O NÚMERO COMPLETO NUNCA É GRAVADO ---
    private String ultimosDigitos(String cartaoNumero) {
        if (cartaoNumero == null || cartaoNumero.length() < DIGITOS_VISIVEIS_DO_CARTAO) {
            return cartaoNumero;
        }

        return cartaoNumero.substring(cartaoNumero.length() - DIGITOS_VISIVEIS_DO_CARTAO);
    }
}
