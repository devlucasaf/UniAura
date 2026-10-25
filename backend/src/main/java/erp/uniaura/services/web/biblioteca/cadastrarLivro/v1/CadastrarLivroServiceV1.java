package erp.uniaura.services.web.biblioteca.cadastrarLivro.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.persistence.entities.Livro;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CadastrarLivroServiceV1 extends BaseService {

    private static final String SUBDIRETORIO_CAPAS = "biblioteca/capas";

    private final StorageService storageService;

    // --- CADASTRA UM LIVRO NO ACERVO, VALIDANDO ISBN E SALVANDO A CAPA ---
    @Transactional
    public Uid cadastrar(CadastrarLivroParamsV1 params, MultipartFile capa) {
        if (params.getIsbn() != null && !params.getIsbn().isBlank()) {
            Long comMesmoIsbn = em()
                    .createQuery("select count(l) from Livro l where l.isbn = :isbn", Long.class)
                    .setParameter("isbn", params.getIsbn())
                    .getSingleResult();

            if (comMesmoIsbn > 0) {
                throw new BusinessException("Já existe um livro cadastrado com o ISBN informado.");
            }
        }

        Livro livro = new Livro();

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
            livro.setCapaUrl(storageService.store(capa, SUBDIRETORIO_CAPAS));
        }

        livro.persist();

        return new Uid(livro.getUid());
    }
}
