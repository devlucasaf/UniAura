package erp.uniaura.services.web.estagio.enviarRelatorioDeEstagio.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.persistence.entities.Estagio;
import erp.uniaura.persistence.entities.RelatorioEstagio;
import erp.uniaura.persistence.entities.StatusEstagio;
import erp.uniaura.persistence.entities.StatusRelatorioEstagio;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.estagio.BuscarEstagioComponent;
import erp.uniaura.services.common.estagio.VerificarAcessoAoEstagioComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EnviarRelatorioDeEstagioServiceV1 extends BaseService {

    private static final String SUBDIRETORIO_RELATORIOS = "relatorios-estagio";

    private final BuscarEstagioComponent buscarEstagioComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarAcessoAoEstagioComponent verificarAcessoAoEstagioComponent;
    private final StorageService storageService;

    // --- ENVIA O RELATÓRIO DE ESTÁGIO DO PERÍODO, VALIDANDO ACESSO E DUPLICIDADE ---
    @Transactional
    public Uid enviar(Long usuarioId, EnviarRelatorioDeEstagioParamsV1 params, MultipartFile comprovante) {
        Estagio estagio = buscarEstagioComponent.buscarPorUid(params.getEstagioUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAcessoAoEstagioComponent.validar(estagio, autenticado);

        if (estagio.getStatus() != StatusEstagio.EM_ANDAMENTO) {
            throw new BusinessException("Só é possível enviar relatórios para um estágio em andamento.");
        }

        Long jaEnviados = em()
                .createQuery("select count(r) from RelatorioEstagio r where r.estagio = :estagio and r.periodoReferencia = :periodo", Long.class)
                .setParameter("estagio", estagio)
                .setParameter("periodo", params.getPeriodoReferencia())
                .getSingleResult();

        if (jaEnviados > 0) {
            throw new BusinessException("Já existe um relatório enviado para o período " + params.getPeriodoReferencia() + ".");
        }

        RelatorioEstagio relatorio = new RelatorioEstagio();

        relatorio.setEstagio(estagio);
        relatorio.setPeriodoReferencia(params.getPeriodoReferencia());
        relatorio.setHorasRegistradas(params.getHorasRegistradas());
        relatorio.setDescricaoAtividades(params.getDescricaoAtividades());
        relatorio.setStatus(StatusRelatorioEstagio.PENDENTE);

        if (comprovante != null && !comprovante.isEmpty()) {
            relatorio.setComprovanteUrl(storageService.store(comprovante, SUBDIRETORIO_RELATORIOS));
        }

        relatorio.persist();

        return new Uid(relatorio.getUid());
    }
}
