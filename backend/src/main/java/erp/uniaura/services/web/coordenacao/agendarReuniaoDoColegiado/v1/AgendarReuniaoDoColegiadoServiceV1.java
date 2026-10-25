package erp.uniaura.services.web.coordenacao.agendarReuniaoDoColegiado.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Curso;
import erp.uniaura.persistence.entities.ReuniaoColegiado;
import erp.uniaura.persistence.entities.StatusReuniao;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.curso.BuscarCursoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgendarReuniaoDoColegiadoServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarCursoComponent   buscarCursoComponent;

    // --- A REUNIÃO NASCE AGENDADA, GUARDANDO QUEM A CRIOU ---
    @Transactional
    public Uid agendar(Long usuarioId, AgendarReuniaoDoColegiadoParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        Curso curso = buscarCursoComponent.buscarPorUid(params.getCursoUid());

        ReuniaoColegiado reuniao = new ReuniaoColegiado();

        reuniao.setCurso(curso);
        reuniao.setTitulo(params.getTitulo());
        reuniao.setDataHora(params.getDataHora());
        reuniao.setLocal(params.getLocal());
        reuniao.setPauta(params.getPauta());
        reuniao.setStatus(StatusReuniao.AGENDADA);
        reuniao.setCriadaPor(autenticado);
        reuniao.persist();

        return new Uid(reuniao.getUid());
    }
}
