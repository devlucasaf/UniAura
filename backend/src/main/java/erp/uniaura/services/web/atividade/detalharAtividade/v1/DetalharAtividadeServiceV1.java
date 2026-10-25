package erp.uniaura.services.web.atividade.detalharAtividade.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Atividade;
import erp.uniaura.services.common.atividade.BuscarAtividadeComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharAtividadeServiceV1 extends BaseService {

    private final BuscarAtividadeComponent buscarAtividadeComponent;

    // --- DEVOLVE OS DADOS DE UMA ATIVIDADE ---
    @Transactional(readOnly = true)
    public DetalharAtividadeRetornoV1 detalhar(DetalharAtividadeParamsV1 params) {
        Atividade atividade = buscarAtividadeComponent.buscarPorUid(params.getUid());

        DetalharAtividadeRetornoV1 retorno = new DetalharAtividadeRetornoV1();

        retorno.setUid(atividade.getUid());
        retorno.setTurmaDisciplinaUid(atividade.getTurmaDisciplina().getUid());
        retorno.setTitulo(atividade.getTitulo());
        retorno.setDescricao(atividade.getDescricao());
        retorno.setTipo(atividade.getTipo());
        retorno.setDataPostagem(atividade.getDataPostagem());
        retorno.setDataEntrega(atividade.getDataEntrega());
        retorno.setValorMaximo(atividade.getValorMaximo());
        retorno.setProfessorNome(atividade.getProfessor().getUsuario().getNome());
        retorno.setAtiva(atividade.getAtiva());
        retorno.setCriadoEm(atividade.getCriadoEm());
        retorno.setAtualizadoEm(atividade.getAtualizadoEm());

        return retorno;
    }
}
