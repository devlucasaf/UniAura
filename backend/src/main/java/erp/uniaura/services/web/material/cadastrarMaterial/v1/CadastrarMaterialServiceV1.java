package erp.uniaura.services.web.material.cadastrarMaterial.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Material;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.material.AplicarArquivoOuLinkDoMaterialComponent;
import erp.uniaura.services.common.professor.VerificarProfessorDaDisciplinaComponent;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CadastrarMaterialServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;
    private final VerificarProfessorDaDisciplinaComponent verificarProfessorDaDisciplinaComponent;
    private final AplicarArquivoOuLinkDoMaterialComponent aplicarArquivoOuLinkDoMaterialComponent;

    // --- CADASTRA O MATERIAL DIDÁTICO COM ARQUIVO OU LINK ---
    @Transactional
    public Uid cadastrar(Long usuarioId, CadastrarMaterialParamsV1 params, MultipartFile arquivo) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        TurmaDisciplina turmaDisciplina = buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid());
        Professor professor = verificarProfessorDaDisciplinaComponent.validar(turmaDisciplina, autenticado);

        Material material = new Material();

        material.setTurmaDisciplina(turmaDisciplina);
        material.setTitulo(params.getTitulo());
        material.setDescricao(params.getDescricao());
        material.setTipo(params.getTipo());
        material.setLinkUrl(params.getLinkUrl());
        material.setProfessor(professor);

        aplicarArquivoOuLinkDoMaterialComponent.aplicar(material, params.getTipo(), params.getLinkUrl(), arquivo);

        material.persist();

        return new Uid(material.getUid());
    }
}
