package erp.uniaura.services.web.aluno.detalharAluno.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.aluno.BuscarAlunoPorMatriculaComponent;
import erp.uniaura.services.common.turma.ConsultarUidDasTurmasComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class DetalharAlunoServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarAlunoPorMatriculaComponent buscarAlunoPorMatriculaComponent;
    private final ConsultarUidDasTurmasComponent consultarUidDasTurmasComponent;

    // --- DETALHA UM ALUNO E RETORNA SEUS DADOS COM A TURMA ATUAL ---
    @Transactional(readOnly = true)
    public DetalharAlunoRetornoV1 detalhar(DetalharAlunoParamsV1 params) {
        Aluno aluno = buscarAluno(params);
        Usuario usuario = aluno.getUsuario();

        DetalharAlunoRetornoV1 retorno = new DetalharAlunoRetornoV1();

        retorno.setUid(aluno.getUid());
        retorno.setNome(usuario.getNome());
        retorno.setEmail(usuario.getEmail());
        retorno.setCpf(usuario.getCpf());
        retorno.setTelefone(usuario.getTelefone());
        retorno.setDataNascimento(usuario.getDataNascimento());
        retorno.setMatriculaRA(aluno.getMatriculaRA());
        retorno.setDataIngresso(aluno.getDataIngresso());
        retorno.setStatus(aluno.getStatus());
        retorno.setCurso(aluno.getCurso());
        retorno.setObservacoes(aluno.getObservacoes());
        retorno.setCriadoEm(aluno.getCriadoEm());
        retorno.setAtualizadoEm(aluno.getAtualizadoEm());

        if (aluno.getTurmaAtualId() != null) {
            retorno.setTurmaAtualUid(consultarUidDasTurmasComponent
                    .uidsPorId(Collections.singleton(aluno.getTurmaAtualId()))
                    .get(aluno.getTurmaAtualId()));
        }

        return retorno;
    }

    // --- O ALUNO É ACHADO PELO UID OU, SEM ELE, PELO RA ---
    private Aluno buscarAluno(DetalharAlunoParamsV1 params) {
        if (params.getUid() != null) {
            return buscarAlunoComponent.buscarPorUid(params.getUid());
        }

        if (params.getMatriculaRA() == null || params.getMatriculaRA().isBlank()) {
            throw new BusinessException("Informe o uid ou a matrícula do aluno.");
        }

        return buscarAlunoPorMatriculaComponent.buscar(params.getMatriculaRA())
                .orElseThrow(() -> new BusinessException("Aluno não encontrado para a matrícula informada."));
    }
}
