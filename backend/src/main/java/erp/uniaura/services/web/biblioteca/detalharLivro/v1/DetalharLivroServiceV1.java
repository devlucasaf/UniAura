package erp.uniaura.services.web.biblioteca.detalharLivro.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Livro;
import erp.uniaura.services.common.biblioteca.BuscarLivroComponent;
import erp.uniaura.services.common.biblioteca.ContagemDeExemplares;
import erp.uniaura.services.common.biblioteca.ContarExemplaresDoLivroComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharLivroServiceV1 extends BaseService {

    private final BuscarLivroComponent buscarLivroComponent;
    private final ContarExemplaresDoLivroComponent contarExemplaresDoLivroComponent;

    // --- DETALHA O LIVRO COM A CONTAGEM DE EXEMPLARES TOTAIS E DISPONÍVEIS ---
    @Transactional(readOnly = true)
    public DetalharLivroRetornoV1 detalhar(DetalharLivroParamsV1 params) {
        Livro livro = buscarLivroComponent.buscarPorUid(params.getUid());
        ContagemDeExemplares exemplares = contarExemplaresDoLivroComponent.contar(livro);

        DetalharLivroRetornoV1 retorno = new DetalharLivroRetornoV1();

        retorno.setUid(livro.getUid());
        retorno.setIsbn(livro.getIsbn());
        retorno.setTitulo(livro.getTitulo());
        retorno.setAutor(livro.getAutor());
        retorno.setEditora(livro.getEditora());
        retorno.setAnoPublicacao(livro.getAnoPublicacao());
        retorno.setEdicao(livro.getEdicao());
        retorno.setPaginas(livro.getPaginas());
        retorno.setCategoria(livro.getCategoria());
        retorno.setSinopse(livro.getSinopse());
        retorno.setCapaUrl(livro.getCapaUrl());
        retorno.setTotalExemplares(exemplares.total());
        retorno.setExemplaresDisponiveis(exemplares.disponiveis());
        retorno.setCriadoEm(livro.getCriadoEm());
        retorno.setAtualizadoEm(livro.getAtualizadoEm());

        return retorno;
    }
}
