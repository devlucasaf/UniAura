package erp.uniaura.services.web.usuario.removerUsuario.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverUsuarioServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;

    @Transactional
    public void remover(RemoverUsuarioParamsV1 params) {
        buscarUsuarioComponent.buscarPorUid(params.getUid()).remove();
    }
}
