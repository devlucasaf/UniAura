package erp.uniaura.services.web.nota.removerNota.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Nota;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.nota.BuscarNotaComponent;
import erp.uniaura.services.common.nota.VerificarPermissaoDeLancamentoDeNotaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverNotaServiceV1 extends BaseService {

    private final BuscarNotaComponent buscarNotaComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarPermissaoDeLancamentoDeNotaComponent verificarPermissaoDeLancamentoDeNotaComponent;

    // --- REMOVE A NOTA APÓS VALIDAR A PERMISSÃO DE LANÇAMENTO ---
    @Transactional
    public void remover(Long usuarioId, RemoverNotaParamsV1 params) {
        Nota nota = buscarNotaComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarPermissaoDeLancamentoDeNotaComponent.validar(autenticado, nota.getTurmaDisciplina());

        nota.remove();
    }
}
