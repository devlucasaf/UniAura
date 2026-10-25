package erp.uniaura.services.web.processo.tramitarProcesso.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Processo;
import erp.uniaura.persistence.entities.StatusProcesso;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.processo.BuscarProcessoComponent;
import erp.uniaura.services.common.processo.RegistrarMovimentacaoDoProcessoComponent;
import erp.uniaura.services.common.processo.ValidarTransicaoDoProcessoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TramitarProcessoServiceV1 extends BaseService {

    private final BuscarProcessoComponent buscarProcessoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final ValidarTransicaoDoProcessoComponent validarTransicaoDoProcessoComponent;
    private final RegistrarMovimentacaoDoProcessoComponent registrarMovimentacaoDoProcessoComponent;

    // --- TRAMITA O PROCESSO, VALIDANDO A TRANSIÇÃO E REGISTRANDO A MOVIMENTAÇÃO ---
    @Transactional
    public void tramitar(Long usuarioId, TramitarProcessoParamsV1 params) {
        Processo processo = buscarProcessoComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        StatusProcesso statusAnterior = processo.getStatus();
        validarTransicaoDoProcessoComponent.validar(statusAnterior, params.getStatus());

        boolean encerrando = params.getStatus() == StatusProcesso.DEFERIDO || params.getStatus() == StatusProcesso.INDEFERIDO;

        if (encerrando && (params.getComentario() == null || params.getComentario().isBlank())) {
            throw new BusinessException("É obrigatório registrar o parecer ao deferir ou indeferir um processo.");
        }

        processo.setStatus(params.getStatus());
        processo.setResponsavel(autenticado);

        if (params.getPrazoResposta() != null) {
            processo.setPrazoResposta(params.getPrazoResposta());
        }

        if (encerrando) {
            processo.setParecerFinal(params.getComentario());
            processo.setEncerradoEm(LocalDateTime.now());
        }

        processo.merge();

        registrarMovimentacaoDoProcessoComponent.registrar(processo, autenticado, statusAnterior, params.getStatus(),
                params.getComentario(), !Boolean.FALSE.equals(params.getVisivelParaAluno()));
    }
}
