package erp.uniaura.services.web.material.alterarMaterial.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Material;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.material.AplicarArquivoOuLinkDoMaterialComponent;
import erp.uniaura.services.common.material.BuscarMaterialComponent;
import erp.uniaura.services.common.material.VerificarAutorizacaoDoMaterialComponent;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AlterarMaterialServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarMaterialComponent buscarMaterialComponent;
    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;
    private final VerificarAutorizacaoDoMaterialComponent verificarAutorizacaoDoMaterialComponent;
    private final AplicarArquivoOuLinkDoMaterialComponent aplicarArquivoOuLinkDoMaterialComponent;

    // --- ALTERA O MATERIAL APÓS VALIDAR A AUTORIZAÇÃO DO USUÁRIO ---
    @Transactional
    public void alterar(Long usuarioId, AlterarMaterialParamsV1 params, MultipartFile arquivo) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        Material material = buscarMaterialComponent.buscarPorUid(params.getUid());

        verificarAutorizacaoDoMaterialComponent.validarPodeEditar(material, autenticado);

        if (!material.getTurmaDisciplina().getUid().equals(params.getTurmaDisciplinaUid())) {
            material.setTurmaDisciplina(buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid()));
        }

        material.setTitulo(params.getTitulo());
        material.setDescricao(params.getDescricao());
        material.setTipo(params.getTipo());
        material.setLinkUrl(params.getLinkUrl());

        aplicarArquivoOuLinkDoMaterialComponent.aplicar(material, params.getTipo(), params.getLinkUrl(), arquivo);

        material.merge();
    }
}
