package erp.uniaura.services.web.tcc.enviarEntregaDeTcc.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.persistence.entities.EntregaTcc;
import erp.uniaura.persistence.entities.StatusEntregaTcc;
import erp.uniaura.persistence.entities.StatusTcc;
import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.tcc.BuscarTccComponent;
import erp.uniaura.services.common.tcc.VerificarAcessoAoTccComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EnviarEntregaDeTccServiceV1 extends BaseService {

    private static final String SUBDIRETORIO_ENTREGAS = "entregas-tcc";

    private final BuscarTccComponent          buscarTccComponent;
    private final BuscarUsuarioComponent      buscarUsuarioComponent;
    private final VerificarAcessoAoTccComponent verificarAcessoAoTccComponent;
    private final StorageService              storageService;

    @Transactional
    public Uid enviar(Long usuarioId, EnviarEntregaDeTccParamsV1 params, MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new BusinessException("É obrigatório enviar um arquivo.");
        }

        Tcc tcc = buscarTccComponent.buscarPorUid(params.getTccUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAcessoAoTccComponent.validar(tcc, autenticado);

        if (tcc.getStatus() != StatusTcc.EM_ANDAMENTO) {
            throw new BusinessException("Só é possível enviar entregas enquanto o TCC está em andamento.");
        }

        EntregaTcc entrega = new EntregaTcc();

        entrega.setTcc(tcc);
        entrega.setTipo(params.getTipo());
        entrega.setArquivoUrl(storageService.store(arquivo, SUBDIRETORIO_ENTREGAS));
        entrega.setStatus(StatusEntregaTcc.PENDENTE);
        entrega.persist();

        return new Uid(entrega.getUid());
    }
}
