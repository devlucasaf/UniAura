package erp.uniaura.services.common.material;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.persistence.entities.Material;
import erp.uniaura.persistence.entities.TipoMaterial;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class AplicarArquivoOuLinkDoMaterialComponent extends BaseComponent {

    private static final String SUBDIRETORIO_MATERIAIS = "materiais";

    private final StorageService storageService;

    // --- MATERIAL DO TIPO LINK GUARDA SÓ O LINK; OS OUTROS TIPOS GUARDAM UM ARQUIVO ---
    public void aplicar(Material material, TipoMaterial tipo, String linkUrl, MultipartFile arquivo) {
        if (tipo == TipoMaterial.LINK) {
            if (linkUrl == null || linkUrl.isBlank()) {
                throw new BusinessException("Para tipo LINK é obrigatório informar 'linkUrl'.");
            }

            if (material.getArquivoUrl() != null) {
                storageService.delete(material.getArquivoUrl());
                material.setArquivoUrl(null);
            }
            return;
        }

        if (arquivo != null && !arquivo.isEmpty()) {
            if (material.getArquivoUrl() != null) {
                storageService.delete(material.getArquivoUrl());
            }

            material.setArquivoUrl(storageService.store(arquivo, SUBDIRETORIO_MATERIAIS));
            material.setLinkUrl(null);
        } else if (material.getArquivoUrl() == null) {
            throw new BusinessException("Para o tipo " + tipo + " é obrigatório enviar um arquivo.");
        }
    }
}
