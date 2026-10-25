package erp.uniaura.services.web.atividade.avaliarEntrega.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.EntregaAtividade;
import erp.uniaura.persistence.entities.StatusEntrega;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.atividade.VerificarAutorizacaoDaAtividadeComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AvaliarEntregaServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarAutorizacaoDaAtividadeComponent verificarAutorizacaoDaAtividadeComponent;

    // --- GRAVA A NOTA E O FEEDBACK DA ENTREGA E A MARCA COMO AVALIADA ---
    @Transactional
    public void avaliar(Long usuarioId, AvaliarEntregaParamsV1 params) {
        EntregaAtividade entrega = findByUid(EntregaAtividade.class, params.getEntregaUid())
                .orElseThrow(() -> new BusinessException("Entrega de atividade não encontrada."));

        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        verificarAutorizacaoDaAtividadeComponent.validarPodeEditar(entrega.getAtividade(), autenticado);

        if (params.getNota().compareTo(entrega.getAtividade().getValorMaximo()) > 0) {
            throw new BusinessException("A nota não pode ultrapassar o valor máximo da atividade (" + entrega.getAtividade().getValorMaximo() + ").");
        }

        entrega.setNota(params.getNota());
        entrega.setFeedback(params.getFeedback());
        entrega.setStatus(StatusEntrega.AVALIADA);
        entrega.merge();
    }
}
