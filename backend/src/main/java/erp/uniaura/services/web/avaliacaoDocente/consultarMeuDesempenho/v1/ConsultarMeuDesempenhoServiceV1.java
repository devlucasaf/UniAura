package erp.uniaura.services.web.avaliacaoDocente.consultarMeuDesempenho.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.services.common.avaliacaoDocente.ResumirAvaliacoesDocenteComponent;
import erp.uniaura.services.common.avaliacaoDocente.ResumoDeAvaliacaoDocente;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultarMeuDesempenhoServiceV1 extends BaseService {

    private final BuscarProfessorComponent buscarProfessorComponent;
    private final ResumirAvaliacoesDocenteComponent resumirAvaliacoesDocenteComponent;

    // --- RETORNA AS MÉDIAS DAS AVALIAÇÕES DO PROFESSOR LOGADO ---
    @Transactional(readOnly = true)
    public ConsultarMeuDesempenhoRetornoV1 consultar(Long usuarioId) {
        Professor professor = buscarProfessorComponent.buscarPorUsuarioId(usuarioId)
                .orElseThrow(() -> new BusinessException("O usuário autenticado não possui vínculo de professor."));

        ResumoDeAvaliacaoDocente resumo = resumirAvaliacoesDocenteComponent.resumirDoProfessor(professor);

        ConsultarMeuDesempenhoRetornoV1 retorno = new ConsultarMeuDesempenhoRetornoV1();

        retorno.setQuantidadeAvaliacoes(resumo.quantidadeAvaliacoes());
        retorno.setMediaDidatica(resumo.mediaDidatica());
        retorno.setMediaPontualidade(resumo.mediaPontualidade());
        retorno.setMediaDisponibilidade(resumo.mediaDisponibilidade());
        retorno.setMediaGeral(resumo.mediaGeral());

        return retorno;
    }
}
