package erp.uniaura.services.web.documento.pesquisarDocumentosDoAluno.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.DocumentoAluno;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarDocumentosDoAlunoServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- PESQUISA DOCUMENTOS DE UM ALUNO COM PAGINAÇÃO E FILTRO POR TIPO ---
    @Transactional(readOnly = true)
    public PesquisarDocumentosDoAlunoRetornoV1 pesquisar(PesquisarDocumentosDoAlunoParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());

        PageableQuery.Result<PesquisarDocumentosDoAlunoDocumentoRetornoV1> resultado =
                PageableQuery.<DocumentoAluno, PesquisarDocumentosDoAlunoDocumentoRetornoV1>builder()
                        .clazz(DocumentoAluno.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            condicoes.add(cb().equal(from.get("aluno"), aluno));

                            if (params.getTipo() != null) {
                                condicoes.add(cb().equal(from.get("tipo"), params.getTipo()));
                            }

                            criteria.where(condicoes.toArray(new Predicate[0]));
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarDocumentosDoAlunoRetornoV1 retorno = new PesquisarDocumentosDoAlunoRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O DOCUMENTO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarDocumentosDoAlunoDocumentoRetornoV1 paraRetorno(DocumentoAluno documento) {
        PesquisarDocumentosDoAlunoDocumentoRetornoV1 item = new PesquisarDocumentosDoAlunoDocumentoRetornoV1();

        item.setUid(documento.getUid());
        item.setAlunoNome(documento.getAluno().getUsuario().getNome());
        item.setTipo(documento.getTipo());
        item.setNomeArquivo(documento.getNomeArquivo());
        item.setArquivoUrl(documento.getArquivoUrl());
        item.setStatus(documento.getStatus());
        item.setVersao(documento.getVersao());
        item.setObservacoes(documento.getObservacoes());

        if (documento.getAnalisadoPor() != null) {
            item.setAnalisadoPorNome(documento.getAnalisadoPor().getNome());
        }

        item.setAnalisadoEm(documento.getAnalisadoEm());
        item.setEnviadoEm(documento.getEnviadoEm());

        return item;
    }
}
