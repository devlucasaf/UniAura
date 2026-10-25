package erp.uniaura.services.web.processo.cancelarProcesso.v1;

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
public class CancelarProcessoServiceV1 extends BaseService {

    private final BuscarProcessoComponent buscarProcessoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final ValidarTransicaoDoProcessoComponent validarTransicaoDoProcessoComponent;
    private final RegistrarMovimentacaoDoProcessoComponent registrarMovimentacaoDoProcessoComponent;

    // --- CANCELA O PROCESSO PELO PRÓPRIO ALUNO E REGISTRA A MOVIMENTAÇÃO ---
    @Transactional
    public void cancelar(Long usuarioId, CancelarProcessoParamsV1 params) {
        Processo processo = buscarProcessoComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        if (!processo.getAluno().getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Apenas o aluno autor do processo pode cancelá-lo.");
        }

        StatusProcesso statusAnterior = processo.getStatus();
        validarTransicaoDoProcessoComponent.validar(statusAnterior, StatusProcesso.CANCELADO);

        processo.setStatus(StatusProcesso.CANCELADO);
        processo.setEncerradoEm(LocalDateTime.now());
        processo.merge();

        String comentario = "Cancelado pelo aluno.";
        if (params.getMotivo() != null && !params.getMotivo().isBlank()) {
            comentario = params.getMotivo();
        }

        registrarMovimentacaoDoProcessoComponent.registrar(processo, autenticado, statusAnterior, StatusProcesso.CANCELADO, comentario, true);
    }
}
