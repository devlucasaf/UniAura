package erp.uniaura.services.common.documento;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.DocumentoAluno;

import org.springframework.stereotype.Component;

@Component
public class BuscarDocumentoDoAlunoComponent extends BaseComponent {

    // --- BUSCA DOCUMENTO DO ALUNO POR UID OU LANÇA EXCEÇÃO ---
    public DocumentoAluno buscarPorUid(Long uid) {
        return findByUid(DocumentoAluno.class, uid)
                .orElseThrow(() -> new BusinessException("Documento não encontrado."));
    }
}
