package erp.uniaura.services.web.comunicado.cadastrarComunicado.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Comunicado;
import erp.uniaura.persistence.entities.PublicoAlvoComunicado;
import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarComunicadoServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarTurmaComponent buscarTurmaComponent;

    // --- CADASTRA UM COMUNICADO E VALIDA A TURMA QUANDO O PÚBLICO-ALVO FOR TURMA ---
    @Transactional
    public Uid cadastrar(Long usuarioId, CadastrarComunicadoParamsV1 params) {
        Usuario autor = buscarUsuarioComponent.buscarPorId(usuarioId);

        Long turmaId = null;
        if (params.getPublicoAlvo() == PublicoAlvoComunicado.TURMA) {
            if (params.getTurmaUid() == null) {
                throw new BusinessException("Informe a turma quando o público-alvo for TURMA.");
            }

            Turma turma = buscarTurmaComponent.buscarPorUid(params.getTurmaUid());
            turmaId = turma.getId();
        }

        Comunicado comunicado = new Comunicado();

        comunicado.setTitulo(params.getTitulo());
        comunicado.setMensagem(params.getMensagem());
        comunicado.setPublicoAlvo(params.getPublicoAlvo());
        comunicado.setTurmaId(turmaId);
        comunicado.setImportante(Boolean.TRUE.equals(params.getImportante()));
        comunicado.setAutor(autor);
        comunicado.persist();

        return new Uid(comunicado.getUid());
    }
}
