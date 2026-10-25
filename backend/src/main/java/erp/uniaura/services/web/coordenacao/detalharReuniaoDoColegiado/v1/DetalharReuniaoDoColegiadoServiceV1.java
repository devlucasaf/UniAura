package erp.uniaura.services.web.coordenacao.detalharReuniaoDoColegiado.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.ParticipanteReuniao;
import erp.uniaura.persistence.entities.ReuniaoColegiado;
import erp.uniaura.services.common.coordenacao.BuscarParticipantesDaReuniaoComponent;
import erp.uniaura.services.common.coordenacao.BuscarReuniaoDoColegiadoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalharReuniaoDoColegiadoServiceV1 extends BaseService {

    private final BuscarReuniaoDoColegiadoComponent buscarReuniaoDoColegiadoComponent;
    private final BuscarParticipantesDaReuniaoComponent buscarParticipantesDaReuniaoComponent;

    // --- DETALHA A REUNIÃO DO COLEGIADO COM SEUS PARTICIPANTES ---
    @Transactional(readOnly = true)
    public DetalharReuniaoDoColegiadoRetornoV1 detalhar(DetalharReuniaoDoColegiadoParamsV1 params) {
        ReuniaoColegiado reuniao = buscarReuniaoDoColegiadoComponent.buscarPorUid(params.getUid());

        DetalharReuniaoDoColegiadoRetornoV1 retorno = new DetalharReuniaoDoColegiadoRetornoV1();

        retorno.setUid(reuniao.getUid());
        retorno.setCursoUid(reuniao.getCurso().getUid());
        retorno.setCursoNome(reuniao.getCurso().getNome());
        retorno.setTitulo(reuniao.getTitulo());
        retorno.setDataHora(reuniao.getDataHora());
        retorno.setLocal(reuniao.getLocal());
        retorno.setPauta(reuniao.getPauta());
        retorno.setStatus(reuniao.getStatus());
        retorno.setDeliberacoes(reuniao.getDeliberacoes());
        retorno.setMotivoCancelamento(reuniao.getMotivoCancelamento());
        retorno.setEncerradaEm(reuniao.getEncerradaEm());
        retorno.setCriadoEm(reuniao.getCriadoEm());
        retorno.setAtualizadoEm(reuniao.getAtualizadoEm());

        if (reuniao.getCriadaPor() != null) {
            retorno.setCriadaPorNome(reuniao.getCriadaPor().getNome());
        }

        List<DetalharReuniaoDoColegiadoParticipanteRetornoV1> participantes = new ArrayList<>();

        for (ParticipanteReuniao participante : buscarParticipantesDaReuniaoComponent.buscar(reuniao)) {
            DetalharReuniaoDoColegiadoParticipanteRetornoV1 item = new DetalharReuniaoDoColegiadoParticipanteRetornoV1();

            item.setUid(participante.getUid());
            item.setUsuarioUid(participante.getUsuario().getUid());
            item.setUsuarioNome(participante.getUsuario().getNome());
            item.setPapel(participante.getPapel());
            item.setPresente(participante.getPresente());

            participantes.add(item);
        }

        retorno.setParticipantes(participantes);

        return retorno;
    }
}
