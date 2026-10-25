package erp.uniaura.services.web.biblioteca.pesquisarLivros.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Livro;
import erp.uniaura.services.common.biblioteca.ContagemDeExemplares;
import erp.uniaura.services.common.biblioteca.ContarExemplaresDoLivroComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PesquisarLivrosServiceV1 extends BaseService {

    private final ContarExemplaresDoLivroComponent contarExemplaresDoLivroComponent;

    // --- PESQUISA LIVROS COM PAGINAÇÃO E FILTROS POR TÍTULO, AUTOR, CATEGORIA E ISBN ---
    @Transactional(readOnly = true)
    public PesquisarLivrosRetornoV1 pesquisar(PesquisarLivrosParamsV1 params) {
        PageableQuery.Result<PesquisarLivrosLivroRetornoV1> resultado =
                PageableQuery.<Livro, PesquisarLivrosLivroRetornoV1>builder()
                        .clazz(Livro.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            if (preenchido(params.getTitulo())) {
                                condicoes.add(cb().like(cb().lower(from.get("titulo")), "%" + minusculo(params.getTitulo()) + "%"));
                            }

                            if (preenchido(params.getAutor())) {
                                condicoes.add(cb().like(cb().lower(from.get("autor")), "%" + minusculo(params.getAutor()) + "%"));
                            }

                            if (preenchido(params.getCategoria())) {
                                condicoes.add(cb().equal(cb().lower(from.get("categoria")), minusculo(params.getCategoria())));
                            }

                            if (preenchido(params.getIsbn())) {
                                condicoes.add(cb().equal(from.get("isbn"), params.getIsbn().trim()));
                            }

                            if (!condicoes.isEmpty()) {
                                criteria.where(condicoes.toArray(new Predicate[0]));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarLivrosRetornoV1 retorno = new PesquisarLivrosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- VERIFICA SE O TEXTO ESTÁ PREENCHIDO ---
    private boolean preenchido(String texto) {
        return texto != null && !texto.isBlank();
    }

    // --- CONVERTE O TEXTO PARA MINÚSCULO E SEM ESPAÇOS ---
    private String minusculo(String texto) {
        return texto.trim().toLowerCase(Locale.ROOT);
    }

    // --- CONVERTE O LIVRO NO ITEM DE RETORNO COM A CONTAGEM DE EXEMPLARES ---
    private PesquisarLivrosLivroRetornoV1 paraRetorno(Livro livro) {
        ContagemDeExemplares exemplares = contarExemplaresDoLivroComponent.contar(livro);

        PesquisarLivrosLivroRetornoV1 item = new PesquisarLivrosLivroRetornoV1();

        item.setUid(livro.getUid());
        item.setIsbn(livro.getIsbn());
        item.setTitulo(livro.getTitulo());
        item.setAutor(livro.getAutor());
        item.setCategoria(livro.getCategoria());
        item.setCapaUrl(livro.getCapaUrl());
        item.setTotalExemplares(exemplares.total());
        item.setExemplaresDisponiveis(exemplares.disponiveis());
        item.setCriadoEm(livro.getCriadoEm());

        return item;
    }
}
