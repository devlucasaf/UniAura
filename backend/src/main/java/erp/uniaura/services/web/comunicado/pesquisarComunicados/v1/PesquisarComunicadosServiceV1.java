package erp.uniaura.services.web.comunicado.pesquisarComunicados.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Comunicado;
import erp.uniaura.services.common.turma.ConsultarUidDasTurmasComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PesquisarComunicadosServiceV1 extends BaseService {

    private final ConsultarUidDasTurmasComponent consultarUidDasTurmasComponent;

    // --- PESQUISA COMUNICADOS COM PAGINAÇÃO E FILTRO POR PÚBLICO-ALVO ---
    @Transactional(readOnly = true)
    public PesquisarComunicadosRetornoV1 pesquisar(PesquisarComunicadosParamsV1 params) {
        PageableQuery.Result<Comunicado> resultado = PageableQuery.<Comunicado, Comunicado>builder()
                .clazz(Comunicado.class)
                .entityManager(em())
                .first(params.getDe())
                .limit(params.getQtd())
                .order(params.getOrdem())
                .predicate((criteria, from) -> {
                    if (params.getPublicoAlvo() != null) {
                        criteria.where(cb().equal(from.get("publicoAlvo"), params.getPublicoAlvo()));
                    }
                })
                .build()
                .query();

        List<Comunicado> comunicados = resultado.getList();
        Map<Long, Long> uidsDasTurmas = consultarUidDasTurmasComponent.uidsPorId(
                comunicados.stream().map(Comunicado::getTurmaId).toList());

        PesquisarComunicadosRetornoV1 retorno = new PesquisarComunicadosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(comunicados.stream().map(comunicado -> paraRetorno(comunicado, uidsDasTurmas)).toList());

        return retorno;
    }

    // --- CONVERTE O COMUNICADO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarComunicadosComunicadoRetornoV1 paraRetorno(Comunicado comunicado, Map<Long, Long> uidsDasTurmas) {
        PesquisarComunicadosComunicadoRetornoV1 item = new PesquisarComunicadosComunicadoRetornoV1();

        item.setUid(comunicado.getUid());
        item.setTitulo(comunicado.getTitulo());
        item.setMensagem(comunicado.getMensagem());
        item.setPublicoAlvo(comunicado.getPublicoAlvo());
        item.setTurmaUid(uidsDasTurmas.get(comunicado.getTurmaId()));
        item.setImportante(comunicado.getImportante());
        item.setAutorUid(comunicado.getAutor().getUid());
        item.setAutorNome(comunicado.getAutor().getNome());
        item.setCriadoEm(comunicado.getCriadoEm());
        item.setAtualizadoEm(comunicado.getAtualizadoEm());

        return item;
    }
}
