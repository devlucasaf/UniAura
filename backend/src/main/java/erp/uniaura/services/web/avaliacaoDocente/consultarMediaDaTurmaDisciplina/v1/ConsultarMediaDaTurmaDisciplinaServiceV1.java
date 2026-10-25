package erp.uniaura.services.web.avaliacaoDocente.consultarMediaDaTurmaDisciplina.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.services.common.avaliacaoDocente.ResumirAvaliacoesDocenteComponent;
import erp.uniaura.services.common.avaliacaoDocente.ResumoDeAvaliacaoDocente;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultarMediaDaTurmaDisciplinaServiceV1 extends BaseService {

    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;
    private final ResumirAvaliacoesDocenteComponent resumirAvaliacoesDocenteComponent;

    // --- RETORNA AS MÉDIAS DAS AVALIAÇÕES DE UMA TURMA/DISCIPLINA ---
    @Transactional(readOnly = true)
    public ConsultarMediaDaTurmaDisciplinaRetornoV1 consultar(ConsultarMediaDaTurmaDisciplinaParamsV1 params) {
        ResumoDeAvaliacaoDocente resumo = resumirAvaliacoesDocenteComponent.resumirDaTurmaDisciplina(
                buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid()));

        ConsultarMediaDaTurmaDisciplinaRetornoV1 retorno = new ConsultarMediaDaTurmaDisciplinaRetornoV1();

        retorno.setQuantidadeAvaliacoes(resumo.quantidadeAvaliacoes());
        retorno.setMediaDidatica(resumo.mediaDidatica());
        retorno.setMediaPontualidade(resumo.mediaPontualidade());
        retorno.setMediaDisponibilidade(resumo.mediaDisponibilidade());
        retorno.setMediaGeral(resumo.mediaGeral());

        return retorno;
    }
}
