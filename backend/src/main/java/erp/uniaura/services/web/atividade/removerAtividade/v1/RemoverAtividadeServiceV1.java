package erp.uniaura.services.web.atividade.removerAtividade.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.persistence.entities.Atividade;
import erp.uniaura.persistence.entities.EntregaAtividade;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.atividade.BuscarAtividadeComponent;
import erp.uniaura.services.common.atividade.VerificarAutorizacaoDaAtividadeComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemoverAtividadeServiceV1 extends BaseService {

    private final BuscarAtividadeComponent buscarAtividadeComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarAutorizacaoDaAtividadeComponent verificarAutorizacaoDaAtividadeComponent;
    private final StorageService storageService;

    // --- REMOVE A ATIVIDADE, SUAS ENTREGAS E OS ARQUIVOS ENVIADOS ---
    @Transactional
    public void remover(Long usuarioId, RemoverAtividadeParamsV1 params) {
        Atividade atividade = buscarAtividadeComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAutorizacaoDaAtividadeComponent.validarPodeEditar(atividade, autenticado);

        List<EntregaAtividade> entregas = em()
                .createQuery("select e from EntregaAtividade e where e.atividade = :atividade", EntregaAtividade.class)
                .setParameter("atividade", atividade)
                .getResultList();

        for (EntregaAtividade entrega : entregas) {
            if (entrega.getArquivoUrl() != null) {
                storageService.delete(entrega.getArquivoUrl());
            }
            entrega.remove();
        }

        atividade.remove();
    }
}
