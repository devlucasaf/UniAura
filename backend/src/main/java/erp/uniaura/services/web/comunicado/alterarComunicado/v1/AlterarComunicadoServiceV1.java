package erp.uniaura.services.web.comunicado.alterarComunicado.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Comunicado;
import erp.uniaura.persistence.entities.PublicoAlvoComunicado;
import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.comunicado.BuscarComunicadoComponent;
import erp.uniaura.services.common.comunicado.VerificarAutorizacaoDoComunicadoComponent;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarComunicadoServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarComunicadoComponent buscarComunicadoComponent;
    private final BuscarTurmaComponent buscarTurmaComponent;
    private final VerificarAutorizacaoDoComunicadoComponent verificarAutorizacaoDoComunicadoComponent;

    // --- ALTERA O COMUNICADO APÓS VALIDAR AUTORIZAÇÃO E TURMA ---
    @Transactional
    public void alterar(Long usuarioId, AlterarComunicadoParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        Comunicado comunicado = buscarComunicadoComponent.buscarPorUid(params.getUid());

        verificarAutorizacaoDoComunicadoComponent.validarPodeEditar(comunicado, autenticado);

        Long turmaId = null;
        if (params.getPublicoAlvo() == PublicoAlvoComunicado.TURMA) {
            if (params.getTurmaUid() == null) {
                throw new BusinessException("Informe a turma quando o público-alvo for TURMA.");
            }

            Turma turma = buscarTurmaComponent.buscarPorUid(params.getTurmaUid());
            turmaId = turma.getId();
        }

        comunicado.setTitulo(params.getTitulo());
        comunicado.setMensagem(params.getMensagem());
        comunicado.setPublicoAlvo(params.getPublicoAlvo());
        comunicado.setTurmaId(turmaId);
        comunicado.setImportante(Boolean.TRUE.equals(params.getImportante()));
        comunicado.merge();
    }
}
