package erp.uniaura.services.web.material.removerMaterial.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.persistence.entities.Material;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.material.BuscarMaterialComponent;
import erp.uniaura.services.common.material.VerificarAutorizacaoDoMaterialComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverMaterialServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarMaterialComponent buscarMaterialComponent;
    private final VerificarAutorizacaoDoMaterialComponent verificarAutorizacaoDoMaterialComponent;
    private final StorageService storageService;

    // --- REMOVE O MATERIAL E SEU ARQUIVO APÓS VALIDAR A AUTORIZAÇÃO ---
    @Transactional
    public void remover(Long usuarioId, RemoverMaterialParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        Material material = buscarMaterialComponent.buscarPorUid(params.getUid());

        verificarAutorizacaoDoMaterialComponent.validarPodeEditar(material, autenticado);

        if (material.getArquivoUrl() != null) {
            storageService.delete(material.getArquivoUrl());
        }

        material.remove();
    }
}
