package erp.uniaura.services.web.ouvidoria.detalharManifestacao.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Manifestacao;
import erp.uniaura.persistence.entities.RespostaManifestacao;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.ouvidoria.BuscarManifestacaoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalharManifestacaoServiceV1 extends BaseService {

    private final BuscarManifestacaoComponent buscarManifestacaoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- DETALHA A MANIFESTAÇÃO RESPEITANDO SIGILO E VISIBILIDADE DAS RESPOSTAS ---
    @Transactional(readOnly = true)
    public DetalharManifestacaoRetornoV1 detalhar(Long usuarioId, DetalharManifestacaoParamsV1 params) {
        Manifestacao manifestacao = buscarManifestacao(params);
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        boolean equipeDaOuvidoria = autenticado.getRole() == TipoUsuario.ADMIN
                || autenticado.getRole() == TipoUsuario.COORDENADOR
                || autenticado.getRole() == TipoUsuario.SECRETARIA;

        if (!equipeDaOuvidoria && !manifestacao.getAutor().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você não tem permissão para consultar esta manifestação.");
        }

        DetalharManifestacaoRetornoV1 retorno = new DetalharManifestacaoRetornoV1();

        retorno.setUid(manifestacao.getUid());
        retorno.setProtocolo(manifestacao.getProtocolo());
        retorno.setTipo(manifestacao.getTipo());
        retorno.setAssunto(manifestacao.getAssunto());
        retorno.setDescricao(manifestacao.getDescricao());
        retorno.setSetor(manifestacao.getSetor());
        retorno.setStatus(manifestacao.getStatus());
        retorno.setAnonima(manifestacao.getAnonima());

        // --- O SIGILO DO AUTOR DE MANIFESTAÇÃO ANÔNIMA VALE ATÉ PARA A EQUIPE ---
        if (!Boolean.TRUE.equals(manifestacao.getAnonima())) {
            retorno.setAutorNome(manifestacao.getAutor().getNome());
        }

        if (manifestacao.getResponsavel() != null) {
            retorno.setResponsavelNome(manifestacao.getResponsavel().getNome());
        }

        retorno.setPrazoResposta(manifestacao.getPrazoResposta());
        retorno.setRespostaFinal(manifestacao.getRespostaFinal());
        retorno.setRespondidoEm(manifestacao.getRespondidoEm());
        retorno.setEncerradoEm(manifestacao.getEncerradoEm());
        retorno.setCriadoEm(manifestacao.getCriadoEm());
        retorno.setAtualizadoEm(manifestacao.getAtualizadoEm());
        retorno.setRespostas(listarRespostas(manifestacao, !equipeDaOuvidoria));

        return retorno;
    }

    // --- BUSCA A MANIFESTAÇÃO PELO UID OU PELO PROTOCOLO ---
    private Manifestacao buscarManifestacao(DetalharManifestacaoParamsV1 params) {
        if (params.getUid() != null) {
            return buscarManifestacaoComponent.buscarPorUid(params.getUid());
        }

        if (params.getProtocolo() != null && !params.getProtocolo().isBlank()) {
            return buscarManifestacaoComponent.buscarPorProtocolo(params.getProtocolo());
        }

        throw new BusinessException("Informe o uid ou o protocolo da manifestação.");
    }

    // --- O AUTOR NÃO ENXERGA AS NOTAS INTERNAS DA EQUIPE ---
    private List<DetalharManifestacaoRespostaRetornoV1> listarRespostas(Manifestacao manifestacao, boolean somenteVisiveis) {
        String consulta = "select r from RespostaManifestacao r where r.manifestacao = :manifestacao ";
        if (somenteVisiveis) {
            consulta = consulta + "and r.visivelParaAutor = true ";
        }
        consulta = consulta + "order by r.criadoEm asc";

        List<RespostaManifestacao> respostas = em()
                .createQuery(consulta, RespostaManifestacao.class)
                .setParameter("manifestacao", manifestacao)
                .getResultList();

        List<DetalharManifestacaoRespostaRetornoV1> lista = new ArrayList<>();

        for (RespostaManifestacao resposta : respostas) {
            DetalharManifestacaoRespostaRetornoV1 item = new DetalharManifestacaoRespostaRetornoV1();

            item.setUid(resposta.getUid());
            item.setAutorNome(resposta.getAutor().getNome());
            item.setStatusAnterior(resposta.getStatusAnterior());
            item.setStatusNovo(resposta.getStatusNovo());
            item.setMensagem(resposta.getMensagem());
            item.setVisivelParaAutor(resposta.getVisivelParaAutor());
            item.setCriadoEm(resposta.getCriadoEm());

            lista.add(item);
        }

        return lista;
    }
}
