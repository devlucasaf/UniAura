package erp.uniaura.services.web.nota.alterarNota.v1;

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
public class AlterarNotaServiceV1 extends BaseService {

    private final BuscarNotaComponent buscarNotaComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarPermissaoDeLancamentoDeNotaComponent verificarPermissaoDeLancamentoDeNotaComponent;

    // --- ALTERA A NOTA APÓS VALIDAR A PERMISSÃO DE LANÇAMENTO ---
    @Transactional
    public void alterar(Long usuarioId, AlterarNotaParamsV1 params) {
        Nota nota = buscarNotaComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarPermissaoDeLancamentoDeNotaComponent.validar(autenticado, nota.getTurmaDisciplina());

        nota.setPeriodoAvaliacao(params.getPeriodoAvaliacao());
        nota.setTipoAvaliacao(params.getTipoAvaliacao());
        nota.setValor(params.getValor());

        if (params.getPeso() != null) {
            nota.setPeso(params.getPeso());
        }

        nota.setObservacoes(params.getObservacoes());
        nota.setLancadaPor(autenticado);
        nota.merge();
    }
}
