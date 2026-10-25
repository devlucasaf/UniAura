package erp.uniaura.services.web.ouvidoria.responderManifestacao.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Manifestacao;
import erp.uniaura.persistence.entities.RespostaManifestacao;
import erp.uniaura.persistence.entities.StatusManifestacao;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.ouvidoria.BuscarManifestacaoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ResponderManifestacaoServiceV1 extends BaseService {

    // --- TRANSIÇÕES PERMITIDAS PARA CADA STATUS ---
    private static final Map<StatusManifestacao, Set<StatusManifestacao>> TRANSICOES = new EnumMap<>(Map.of(
            StatusManifestacao.ABERTA,
            Set.of(StatusManifestacao.EM_APURACAO, StatusManifestacao.RESPONDIDA, StatusManifestacao.ARQUIVADA),
            StatusManifestacao.EM_APURACAO,
            Set.of(StatusManifestacao.RESPONDIDA, StatusManifestacao.ARQUIVADA),
            StatusManifestacao.RESPONDIDA,
            Set.of(StatusManifestacao.EM_APURACAO, StatusManifestacao.ENCERRADA, StatusManifestacao.ARQUIVADA)
    ));

    private final BuscarManifestacaoComponent buscarManifestacaoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- RESPONDE A MANIFESTAÇÃO VALIDANDO A TRANSIÇÃO DE STATUS E GRAVANDO O HISTÓRICO ---
    @Transactional
    public void responder(Long usuarioId, ResponderManifestacaoParamsV1 params) {
        Manifestacao manifestacao = buscarManifestacaoComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        StatusManifestacao statusAnterior = manifestacao.getStatus();

        if (statusAnterior.isFinal()) {
            throw new BusinessException("A manifestação já está %s e não pode ser movimentada.".formatted(statusAnterior));
        }

        if (statusAnterior == params.getStatus()) {
            throw new BusinessException("A manifestação já está com o status %s.".formatted(params.getStatus()));
        }

        if (!TRANSICOES.getOrDefault(statusAnterior, Set.of()).contains(params.getStatus())) {
            throw new BusinessException("Não é permitido mudar o status de %s para %s.".formatted(statusAnterior, params.getStatus()));
        }

        manifestacao.setStatus(params.getStatus());
        manifestacao.setResponsavel(autenticado);

        if (params.getPrazoResposta() != null) {
            manifestacao.setPrazoResposta(params.getPrazoResposta());
        }

        if (params.getStatus() == StatusManifestacao.RESPONDIDA) {
            manifestacao.setRespostaFinal(params.getMensagem());
            manifestacao.setRespondidoEm(LocalDateTime.now());
        }

        if (params.getStatus().isFinal()) {
            manifestacao.setEncerradoEm(LocalDateTime.now());
        }

        manifestacao.merge();

        RespostaManifestacao resposta = new RespostaManifestacao();

        resposta.setManifestacao(manifestacao);
        resposta.setAutor(autenticado);
        resposta.setStatusAnterior(statusAnterior);
        resposta.setStatusNovo(params.getStatus());
        resposta.setMensagem(params.getMensagem());
        resposta.setVisivelParaAutor(!Boolean.FALSE.equals(params.getVisivelParaAutor()));
        resposta.persist();
    }
}
