package erp.uniaura.services.web.atividade.alterarAtividade.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Atividade;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.atividade.BuscarAtividadeComponent;
import erp.uniaura.services.common.atividade.VerificarAutorizacaoDaAtividadeComponent;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarAtividadeServiceV1 extends BaseService {

    private final BuscarAtividadeComponent buscarAtividadeComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;
    private final VerificarAutorizacaoDaAtividadeComponent verificarAutorizacaoDaAtividadeComponent;

    // --- ATUALIZA OS METADADOS DE UMA ATIVIDADE; SÓ O PROFESSOR DONO, A COORDENAÇÃO OU O ADMIN PODEM ALTERAR ---
    @Transactional
    public void alterar(Long usuarioId, AlterarAtividadeParamsV1 params) {
        Atividade atividade = buscarAtividadeComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAutorizacaoDaAtividadeComponent.validarPodeEditar(atividade, autenticado);

        if (params.getDataEntrega().isBefore(atividade.getDataPostagem())) {
            throw new BusinessException("A data de entrega não pode ser anterior à data de postagem.");
        }

        if (!atividade.getTurmaDisciplina().getUid().equals(params.getTurmaDisciplinaUid())) {
            atividade.setTurmaDisciplina(buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid()));
        }

        atividade.setTitulo(params.getTitulo());
        atividade.setDescricao(params.getDescricao());
        atividade.setTipo(params.getTipo());
        atividade.setDataEntrega(params.getDataEntrega());
        atividade.setValorMaximo(params.getValorMaximo());

        if (params.getAtiva() != null) {
            atividade.setAtiva(params.getAtiva());
        }

        atividade.merge();
    }
}
