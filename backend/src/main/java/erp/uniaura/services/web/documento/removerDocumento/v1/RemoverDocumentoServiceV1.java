package erp.uniaura.services.web.documento.removerDocumento.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.persistence.entities.DocumentoAluno;
import erp.uniaura.persistence.entities.StatusDocumento;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.documento.BuscarDocumentoDoAlunoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverDocumentoServiceV1 extends BaseService {

    private final BuscarDocumentoDoAlunoComponent buscarDocumentoDoAlunoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final StorageService storageService;

    // --- REMOVE O DOCUMENTO E O ARQUIVO, VALIDANDO PERMISSÃO E STATUS ---
    @Transactional
    public void remover(Long usuarioId, RemoverDocumentoParamsV1 params) {
        DocumentoAluno documento = buscarDocumentoDoAlunoComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        boolean equipe = autenticado.getRole() == TipoUsuario.ADMIN
                || autenticado.getRole() == TipoUsuario.COORDENADOR
                || autenticado.getRole() == TipoUsuario.SECRETARIA;

        if (!equipe) {
            if (!documento.getAluno().getUsuario().getId().equals(autenticado.getId())) {
                throw new BusinessException("Você não pode remover documentos de outro aluno.");
            }

            if (documento.getStatus() != StatusDocumento.PENDENTE) {
                throw new BusinessException("Só é possível remover documentos ainda pendentes de análise.");
            }
        }

        storageService.delete(documento.getArquivoUrl());
        documento.remove();
    }
}
