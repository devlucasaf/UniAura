package erp.uniaura.services.web.documento.enviarDocumentoDoAluno.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.DocumentoAluno;
import erp.uniaura.persistence.entities.StatusDocumento;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EnviarDocumentoDoAlunoServiceV1 extends BaseService {

    private static final String SUBDIRETORIO_DOCUMENTOS = "documentos-alunos";

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarAlunoComponent buscarAlunoComponent;
    private final StorageService storageService;

    // --- ENVIA O DOCUMENTO DO ALUNO, GRAVANDO O ARQUIVO E A VERSÃO ---
    @Transactional
    public Uid enviar(Long usuarioId, EnviarDocumentoDoAlunoParamsV1 params, MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new BusinessException("É obrigatório enviar um arquivo.");
        }

        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        Aluno aluno = resolverAluno(autenticado, params);

        Long enviados = em()
                .createQuery("select count(d) from DocumentoAluno d where d.aluno = :aluno and d.tipo = :tipo", Long.class)
                .setParameter("aluno", aluno)
                .setParameter("tipo", params.getTipo())
                .getSingleResult();

        DocumentoAluno documento = new DocumentoAluno();

        documento.setAluno(aluno);
        documento.setTipo(params.getTipo());
        documento.setNomeArquivo(arquivo.getOriginalFilename());
        documento.setArquivoUrl(storageService.store(arquivo, SUBDIRETORIO_DOCUMENTOS));
        documento.setStatus(StatusDocumento.PENDENTE);
        documento.setVersao(enviados.intValue() + 1);
        documento.persist();

        return new Uid(documento.getUid());
    }

    // --- O ALUNO ENVIA PARA SI MESMO; A EQUIPE ENVIA PARA O ALUNO INFORMADO ---
    private Aluno resolverAluno(Usuario autenticado, EnviarDocumentoDoAlunoParamsV1 params) {
        if (autenticado.getRole() == TipoUsuario.ALUNO) {
            return buscarAlunoComponent.buscarPorUsuarioId(autenticado.getId());
        }

        if (params.getAlunoUid() == null) {
            throw new BusinessException("Informe o aluno para o qual o documento está sendo enviado.");
        }

        return buscarAlunoComponent.buscarPorUid(params.getAlunoUid());
    }
}
