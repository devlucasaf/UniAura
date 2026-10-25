package erp.uniaura.services.web.atividade.cadastrarAtividade.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Atividade;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.professor.VerificarProfessorDaDisciplinaComponent;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CadastrarAtividadeServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;
    private final VerificarProfessorDaDisciplinaComponent verificarProfessorDaDisciplinaComponent;

    // --- POSTA UMA NOVA ATIVIDADE NA TURMA E DISCIPLINA DO VÍNCULO INFORMADO ---
    @Transactional
    public Uid cadastrar(Long usuarioId, CadastrarAtividadeParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        TurmaDisciplina turmaDisciplina = buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid());
        Professor professor = verificarProfessorDaDisciplinaComponent.validar(turmaDisciplina, autenticado);

        LocalDateTime agora = LocalDateTime.now();

        if (params.getDataEntrega().isBefore(agora)) {
            throw new BusinessException("A data de entrega não pode ser anterior à data de postagem.");
        }

        Atividade atividade = new Atividade();

        atividade.setTurmaDisciplina(turmaDisciplina);
        atividade.setTitulo(params.getTitulo());
        atividade.setDescricao(params.getDescricao());
        atividade.setTipo(params.getTipo());
        atividade.setDataPostagem(agora);
        atividade.setDataEntrega(params.getDataEntrega());
        atividade.setValorMaximo(params.getValorMaximo());
        atividade.setProfessor(professor);
        atividade.setAtiva(!Boolean.FALSE.equals(params.getAtiva()));
        atividade.persist();

        return new Uid(atividade.getUid());
    }
}
