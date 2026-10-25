package erp.uniaura.services.web.documento.pesquisarMeusDocumentos.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.DocumentoAluno;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarMeusDocumentosServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- PESQUISA OS DOCUMENTOS DO ALUNO LOGADO COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarMeusDocumentosRetornoV1 pesquisar(Long usuarioId, PesquisarMeusDocumentosParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUsuarioId(usuarioId);

        PageableQuery.Result<PesquisarMeusDocumentosDocumentoRetornoV1> resultado =
                PageableQuery.<DocumentoAluno, PesquisarMeusDocumentosDocumentoRetornoV1>builder()
                        .clazz(DocumentoAluno.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("aluno"), aluno)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarMeusDocumentosRetornoV1 retorno = new PesquisarMeusDocumentosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O DOCUMENTO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarMeusDocumentosDocumentoRetornoV1 paraRetorno(DocumentoAluno documento) {
        PesquisarMeusDocumentosDocumentoRetornoV1 item = new PesquisarMeusDocumentosDocumentoRetornoV1();

        item.setUid(documento.getUid());
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
