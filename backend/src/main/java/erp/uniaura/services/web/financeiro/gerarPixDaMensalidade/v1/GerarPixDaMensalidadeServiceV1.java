package erp.uniaura.services.web.financeiro.gerarPixDaMensalidade.v1;

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
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GerarPixDaMensalidadeServiceV1 extends BaseService {

    private static final int MINUTOS_EXPIRACAO_PIX = 30;

    private final BuscarMensalidadeComponent buscarMensalidadeComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarAcessoAMensalidadeComponent verificarAcessoAMensalidadeComponent;
    private final VerificarMensalidadeEmAbertoComponent verificarMensalidadeEmAbertoComponent;
    private final CalcularMultaDaMensalidadeComponent calcularMultaDaMensalidadeComponent;

    // --- GERA O PIX DA MENSALIDADE EM ABERTO COM O VALOR ATUALIZADO ---
    @Transactional(readOnly = true)
    public GerarPixDaMensalidadeRetornoV1 gerar(Long usuarioId, GerarPixDaMensalidadeParamsV1 params) {
        Mensalidade mensalidade = buscarMensalidadeComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAcessoAMensalidadeComponent.validar(mensalidade, autenticado);
        verificarMensalidadeEmAbertoComponent.validar(mensalidade);

        BigDecimal valorTotal = calcularMultaDaMensalidadeComponent.calcularValorTotal(mensalidade);

        String payload = "00020126580014BR.GOV.BCB.PIX0136uniaura-ficticio-%06d520400005303986540%s5802BR5913UNIAURA ERP6009SAO PAULO62070503***6304FICT"
                .formatted(mensalidade.getId(), valorTotal.setScale(2, RoundingMode.HALF_UP));

        GerarPixDaMensalidadeRetornoV1 retorno = new GerarPixDaMensalidadeRetornoV1();

        retorno.setPayload(payload);
        retorno.setValor(valorTotal);
        retorno.setExpiraEm(LocalDateTime.now().plusMinutes(MINUTOS_EXPIRACAO_PIX));

        return retorno;
    }
}
