package erp.uniaura.services.web.ouvidoria.registrarManifestacao.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.protocolo.GeradorProtocolo;
import erp.uniaura.persistence.entities.Manifestacao;
import erp.uniaura.persistence.entities.StatusManifestacao;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrarManifestacaoServiceV1 extends BaseService {

    private static final String PREFIXO_PROTOCOLO = "OUV";

    private static final int MAXIMO_DE_TENTATIVAS_DO_PROTOCOLO = 5;

    private final GeradorProtocolo geradorProtocolo;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- REGISTRA A MANIFESTAÇÃO COM PROTOCOLO ÚNICO E STATUS ABERTA ---
    @Transactional
    public RegistrarManifestacaoRetornoV1 registrar(Long usuarioId, RegistrarManifestacaoParamsV1 params) {
        Manifestacao manifestacao = new Manifestacao();

        manifestacao.setProtocolo(gerarProtocoloUnico());
        manifestacao.setAutor(buscarUsuarioComponent.buscarPorId(usuarioId));
        manifestacao.setAnonima(Boolean.TRUE.equals(params.getAnonima()));
        manifestacao.setTipo(params.getTipo());
        manifestacao.setAssunto(params.getAssunto());
        manifestacao.setDescricao(params.getDescricao());
        manifestacao.setSetor(params.getSetor());
        manifestacao.setStatus(StatusManifestacao.ABERTA);
        manifestacao.persist();

        RegistrarManifestacaoRetornoV1 retorno = new RegistrarManifestacaoRetornoV1();

        retorno.setUid(manifestacao.getUid());
        retorno.setProtocolo(manifestacao.getProtocolo());

        return retorno;
    }

    // --- SORTEIA UM PROTOCOLO ATÉ ENCONTRAR UM QUE AINDA NÃO ESTEJA EM USO ---
    private String gerarProtocoloUnico() {
        for (int tentativa = 0; tentativa < MAXIMO_DE_TENTATIVAS_DO_PROTOCOLO; tentativa++) {
            String protocolo = geradorProtocolo.gerar(PREFIXO_PROTOCOLO);

            Long emUso = em()
                    .createQuery("select count(m) from Manifestacao m where m.protocolo = :protocolo", Long.class)
                    .setParameter("protocolo", protocolo)
                    .getSingleResult();

            if (emUso == 0) {
                return protocolo;
            }
        }

        throw new BusinessException("Não foi possível gerar um número de protocolo. Tente novamente.");
    }
}
