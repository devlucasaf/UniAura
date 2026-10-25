package erp.uniaura.services.web.avaliacaoDocente.consultarMediaDoProfessor.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.services.common.avaliacaoDocente.ResumirAvaliacoesDocenteComponent;
import erp.uniaura.services.common.avaliacaoDocente.ResumoDeAvaliacaoDocente;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultarMediaDoProfessorServiceV1 extends BaseService {

    private final BuscarProfessorComponent buscarProfessorComponent;
    private final ResumirAvaliacoesDocenteComponent resumirAvaliacoesDocenteComponent;

    // --- RETORNA AS MÉDIAS CONSOLIDADAS DAS AVALIAÇÕES DE UM PROFESSOR ---
    @Transactional(readOnly = true)
    public ConsultarMediaDoProfessorRetornoV1 consultar(ConsultarMediaDoProfessorParamsV1 params) {
        ResumoDeAvaliacaoDocente resumo = resumirAvaliacoesDocenteComponent.resumirDoProfessor(
                buscarProfessorComponent.buscarPorUid(params.getProfessorUid()));

        ConsultarMediaDoProfessorRetornoV1 retorno = new ConsultarMediaDoProfessorRetornoV1();

        retorno.setQuantidadeAvaliacoes(resumo.quantidadeAvaliacoes());
        retorno.setMediaDidatica(resumo.mediaDidatica());
        retorno.setMediaPontualidade(resumo.mediaPontualidade());
        retorno.setMediaDisponibilidade(resumo.mediaDisponibilidade());
        retorno.setMediaGeral(resumo.mediaGeral());

        return retorno;
    }
}
