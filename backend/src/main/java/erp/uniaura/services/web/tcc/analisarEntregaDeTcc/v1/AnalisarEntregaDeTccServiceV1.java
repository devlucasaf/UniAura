package erp.uniaura.services.web.tcc.analisarEntregaDeTcc.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.EntregaTcc;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.StatusEntregaTcc;
import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalisarEntregaDeTccServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;

    @Transactional
    public void analisar(Long usuarioId, AnalisarEntregaDeTccParamsV1 params) {
        EntregaTcc entrega = findByUid(EntregaTcc.class, params.getEntregaUid())
                .orElseThrow(() -> new BusinessException("Entrega de TCC não encontrada."));

        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        validarOrientador(entrega.getTcc(), autenticado);

        if (params.getStatus() == StatusEntregaTcc.PENDENTE) {
            throw new BusinessException("O resultado da análise deve ser APROVADA ou REJEITADA.");
        }

        if (entrega.getStatus() != StatusEntregaTcc.PENDENTE) {
            throw new BusinessException("Esta entrega já foi analisada.");
        }

        entrega.setStatus(params.getStatus());
        entrega.setObservacoesOrientador(params.getObservacoesOrientador());
        entrega.setAnalisadoEm(LocalDateTime.now());
        entrega.merge();
    }

    // --- O PROFESSOR SÓ ANALISA ENTREGAS DE TCC EM QUE É ORIENTADOR OU COORIENTADOR; COORDENAÇÃO E ADMIN ANALISAM QUALQUER UMA ---
    private void validarOrientador(Tcc tcc, Usuario autenticado) {
        if (autenticado.getRole() != TipoUsuario.PROFESSOR) {
            return;
        }

        if (ehUsuarioDoProfessor(tcc.getProfessorOrientador(), autenticado)
                || ehUsuarioDoProfessor(tcc.getProfessorCoorientador(), autenticado)) {
            return;
        }

        throw new BusinessException("Apenas o orientador ou o coorientador pode analisar as entregas deste TCC.");
    }

    private boolean ehUsuarioDoProfessor(Professor professor, Usuario autenticado) {
        return professor != null && professor.getUsuario().getId().equals(autenticado.getId());
    }
}
