package erp.uniaura.services.web.biblioteca.alterarLivro.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.persistence.entities.Livro;
import erp.uniaura.services.common.biblioteca.BuscarLivroComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AlterarLivroServiceV1 extends BaseService {

    private static final String SUBDIRETORIO_CAPAS = "biblioteca/capas";

    private final BuscarLivroComponent buscarLivroComponent;
    private final StorageService storageService;

    // --- ALTERA OS DADOS DO LIVRO E SUBSTITUI A CAPA SE ENVIADA ---
    @Transactional
    public void alterar(AlterarLivroParamsV1 params, MultipartFile capa) {
        Livro livro = buscarLivroComponent.buscarPorUid(params.getUid());

        if (params.getIsbn() != null && !params.getIsbn().isBlank()) {
            Long emOutroLivro = em()
                    .createQuery("select count(l) from Livro l where l.isbn = :isbn and l <> :livro", Long.class)
                    .setParameter("isbn", params.getIsbn())
                    .setParameter("livro", livro)
                    .getSingleResult();

            if (emOutroLivro > 0) {
                throw new BusinessException("Já existe um livro cadastrado com o ISBN informado.");
            }
        }

        livro.setIsbn(params.getIsbn());
        livro.setTitulo(params.getTitulo());
        livro.setAutor(params.getAutor());
        livro.setEditora(params.getEditora());
        livro.setAnoPublicacao(params.getAnoPublicacao());
        livro.setEdicao(params.getEdicao());
        livro.setPaginas(params.getPaginas());
        livro.setCategoria(params.getCategoria());
        livro.setSinopse(params.getSinopse());

        if (capa != null && !capa.isEmpty()) {
            if (livro.getCapaUrl() != null) {
                storageService.delete(livro.getCapaUrl());
            }

            livro.setCapaUrl(storageService.store(capa, SUBDIRETORIO_CAPAS));
        }

        livro.merge();
    }
}
