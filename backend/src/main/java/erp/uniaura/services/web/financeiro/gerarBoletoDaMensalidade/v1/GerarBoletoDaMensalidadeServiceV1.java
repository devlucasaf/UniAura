package erp.uniaura.services.web.financeiro.gerarBoletoDaMensalidade.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Mensalidade;
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
public class GerarBoletoDaMensalidadeServiceV1 extends BaseService {

    private static final BigDecimal CEM = BigDecimal.valueOf(100);

    private final BuscarMensalidadeComponent buscarMensalidadeComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarAcessoAMensalidadeComponent verificarAcessoAMensalidadeComponent;
    private final VerificarMensalidadeEmAbertoComponent verificarMensalidadeEmAbertoComponent;
    private final CalcularMultaDaMensalidadeComponent calcularMultaDaMensalidadeComponent;

    // --- GERA O BOLETO DA MENSALIDADE EM ABERTO COM O VALOR ATUALIZADO ---
    @Transactional(readOnly = true)
    public GerarBoletoDaMensalidadeRetornoV1 gerar(Long usuarioId, GerarBoletoDaMensalidadeParamsV1 params) {
        Mensalidade mensalidade = buscarMensalidadeComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAcessoAMensalidadeComponent.validar(mensalidade, autenticado);
        verificarMensalidadeEmAbertoComponent.validar(mensalidade);

        BigDecimal valorTotal = calcularMultaDaMensalidadeComponent.calcularValorTotal(mensalidade);
        long centavos = valorTotal.multiply(CEM).longValue();

        String codigoBarras = "%03d9%01d%010d%010d%010d"
                .formatted(237, 9, centavos, mensalidade.getId(), mensalidade.getId() * 7 + 13);

        String linhaDigitavel = "%s.%s %s.%s %s.%s %s %s".formatted(
                codigoBarras.substring(0, 5),
                codigoBarras.substring(5, 10),
                codigoBarras.substring(10, 15),
                codigoBarras.substring(15, 21),
                codigoBarras.substring(21, 26),
                codigoBarras.substring(26, 32),
                codigoBarras.substring(32, 33),
                codigoBarras.substring(33)
        );

        GerarBoletoDaMensalidadeRetornoV1 retorno = new GerarBoletoDaMensalidadeRetornoV1();

        retorno.setLinhaDigitavel(linhaDigitavel);
        retorno.setCodigoBarras(codigoBarras);
        retorno.setValor(valorTotal);
        retorno.setVencimento(mensalidade.getVencimento());

        return retorno;
    }
}
