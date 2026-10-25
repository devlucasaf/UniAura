package erp.uniaura.services.web.atividade.entregarAtividade.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Atividade;
import erp.uniaura.persistence.entities.EntregaAtividade;
import erp.uniaura.persistence.entities.StatusEntrega;
import erp.uniaura.persistence.entities.StatusMatricula;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.atividade.BuscarAtividadeComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntregarAtividadeServiceV1 extends BaseService {

    private static final String SUBDIRETORIO_ENTREGAS = "atividades/entregas";

    private final BuscarAtividadeComponent buscarAtividadeComponent;
    private final BuscarAlunoComponent buscarAlunoComponent;
    private final StorageService storageService;

    // --- O ALUNO ENTREGA O ARQUIVO DA ATIVIDADE ---
    @Transactional
    public Uid entregar(Long usuarioId, EntregarAtividadeParamsV1 params, MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new BusinessException("É obrigatório enviar um arquivo.");
        }

        Atividade atividade = buscarAtividadeComponent.buscarPorUid(params.getAtividadeUid());

        if (Boolean.FALSE.equals(atividade.getAtiva())) {
            throw new BusinessException("Esta atividade não está mais ativa.");
        }

        Aluno aluno = buscarAlunoComponent.buscarPorUsuarioId(usuarioId);

        Long matriculasAtivas = em()
                .createQuery("select count(m) from Matricula m where m.aluno = :aluno and m.turma = :turma and m.status = :status", Long.class)
                .setParameter("aluno", aluno)
                .setParameter("turma", atividade.getTurmaDisciplina().getTurma())
                .setParameter("status", StatusMatricula.ATIVA)
                .getSingleResult();

        if (matriculasAtivas == 0) {
            throw new BusinessException("Aluno não possui matrícula ATIVA na turma desta atividade.");
        }

        List<EntregaAtividade> anteriores = em()
                .createQuery("select e from EntregaAtividade e where e.atividade = :atividade and e.aluno = :aluno", EntregaAtividade.class)
                .setParameter("atividade", atividade)
                .setParameter("aluno", aluno)
                .getResultList();

        EntregaAtividade entrega = new EntregaAtividade();
        boolean nova = anteriores.isEmpty();

        if (nova) {
            entrega.setAtividade(atividade);
            entrega.setAluno(aluno);
        } else {
            entrega = anteriores.get(0);
        }

        if (entrega.getStatus() == StatusEntrega.AVALIADA) {
            throw new BusinessException("Esta entrega já foi avaliada e não pode mais ser modificada.");
        }

        if (entrega.getArquivoUrl() != null) {
            storageService.delete(entrega.getArquivoUrl());
        }

        LocalDateTime agora = LocalDateTime.now();

        entrega.setArquivoUrl(storageService.store(arquivo, SUBDIRETORIO_ENTREGAS));
        entrega.setComentarioAluno(params.getComentarioAluno());
        entrega.setDataEntrega(agora);

        if (agora.isAfter(atividade.getDataEntrega())) {
            entrega.setStatus(StatusEntrega.ATRASADA);
        } else {
            entrega.setStatus(StatusEntrega.ENTREGUE);
        }

        if (nova) {
            entrega.persist();
        } else {
            entrega.merge();
        }

        return new Uid(entrega.getUid());
    }
}
