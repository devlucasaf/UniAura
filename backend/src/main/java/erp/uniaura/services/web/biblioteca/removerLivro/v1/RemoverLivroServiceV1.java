package erp.uniaura.services.web.biblioteca.removerLivro.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.persistence.entities.Livro;
import erp.uniaura.services.common.biblioteca.BuscarLivroComponent;
import erp.uniaura.services.common.biblioteca.ContarExemplaresDoLivroComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverLivroServiceV1 extends BaseService {

    private final BuscarLivroComponent buscarLivroComponent;
    private final ContarExemplaresDoLivroComponent contarExemplaresDoLivroComponent;
    private final StorageService storageService;

    // --- REMOVE O LIVRO SEM EXEMPLARES E APAGA SUA CAPA ---
    @Transactional
    public void remover(RemoverLivroParamsV1 params) {
        Livro livro = buscarLivroComponent.buscarPorUid(params.getUid());

        if (contarExemplaresDoLivroComponent.contar(livro).total() > 0) {
            throw new BusinessException("Não é possível excluir livro com exemplares cadastrados.");
        }

        if (livro.getCapaUrl() != null) {
            storageService.delete(livro.getCapaUrl());
        }

        livro.remove();
    }
}
