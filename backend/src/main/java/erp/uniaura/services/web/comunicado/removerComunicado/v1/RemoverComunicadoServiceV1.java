package erp.uniaura.services.web.comunicado.removerComunicado.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Comunicado;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.comunicado.BuscarComunicadoComponent;
import erp.uniaura.services.common.comunicado.VerificarAutorizacaoDoComunicadoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverComunicadoServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarComunicadoComponent buscarComunicadoComponent;
    private final VerificarAutorizacaoDoComunicadoComponent verificarAutorizacaoDoComunicadoComponent;

    // --- REMOVE O COMUNICADO APÓS VALIDAR A AUTORIZAÇÃO ---
    @Transactional
    public void remover(Long usuarioId, RemoverComunicadoParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        Comunicado comunicado = buscarComunicadoComponent.buscarPorUid(params.getUid());

        verificarAutorizacaoDoComunicadoComponent.validarPodeEditar(comunicado, autenticado);

        comunicado.remove();
    }
}
