package erp.uniaura.services.web.certificado.pesquisarCertificadosEmitidos.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.CertificadoEmitido;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarCertificadosEmitidosServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- PESQUISA OS CERTIFICADOS EMITIDOS DE UM ALUNO COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarCertificadosEmitidosRetornoV1 pesquisar(PesquisarCertificadosEmitidosParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());

        PageableQuery.Result<PesquisarCertificadosEmitidosCertificadoRetornoV1> resultado =
                PageableQuery.<CertificadoEmitido, PesquisarCertificadosEmitidosCertificadoRetornoV1>builder()
                        .clazz(CertificadoEmitido.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("aluno"), aluno)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarCertificadosEmitidosRetornoV1 retorno = new PesquisarCertificadosEmitidosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O CERTIFICADO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarCertificadosEmitidosCertificadoRetornoV1 paraRetorno(CertificadoEmitido certificado) {
        PesquisarCertificadosEmitidosCertificadoRetornoV1 item = new PesquisarCertificadosEmitidosCertificadoRetornoV1();

        item.setUid(certificado.getUid());
        item.setTipo(certificado.getTipo());
        item.setCodigoVerificacao(certificado.getCodigoVerificacao());
        item.setEmitidoPorNome(certificado.getEmitidoPor().getNome());
        item.setEmitidoEm(certificado.getEmitidoEm());

        return item;
    }
}
