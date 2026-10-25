package erp.uniaura.services.web.material.detalharMaterial.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Material;
import erp.uniaura.services.common.material.BuscarMaterialComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharMaterialServiceV1 extends BaseService {

    private final BuscarMaterialComponent buscarMaterialComponent;

    // --- DETALHA O MATERIAL PELO UID ---
    @Transactional(readOnly = true)
    public DetalharMaterialRetornoV1 detalhar(DetalharMaterialParamsV1 params) {
        Material material = buscarMaterialComponent.buscarPorUid(params.getUid());

        DetalharMaterialRetornoV1 retorno = new DetalharMaterialRetornoV1();

        retorno.setUid(material.getUid());
        retorno.setTurmaDisciplinaUid(material.getTurmaDisciplina().getUid());
        retorno.setTitulo(material.getTitulo());
        retorno.setDescricao(material.getDescricao());
        retorno.setTipo(material.getTipo());
        retorno.setArquivoUrl(material.getArquivoUrl());
        retorno.setLinkUrl(material.getLinkUrl());
        retorno.setProfessorUid(material.getProfessor().getUid());
        retorno.setProfessorNome(material.getProfessor().getUsuario() == null ? null : material.getProfessor().getUsuario().getNome());
        retorno.setCriadoEm(material.getCriadoEm());
        retorno.setAtualizadoEm(material.getAtualizadoEm());

        return retorno;
    }
}
