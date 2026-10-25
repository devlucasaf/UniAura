package erp.uniaura.services.common.turma;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.TurmaDisciplina;

import org.springframework.stereotype.Component;

@Component
public class BuscarTurmaDisciplinaComponent extends BaseComponent {

    // --- PELO UID, QUE É O IDENTIFICADOR PÚBLICO DAS FUNCIONALIDADES NOVAS ---
    public TurmaDisciplina buscarPorUid(Long uid) {
        return findByUid(TurmaDisciplina.class, uid)
                .orElseThrow(() -> new BusinessException("Vínculo entre turma e disciplina não encontrado."));
    }

    // --- USO TRANSITÓRIO DE ATIVIDADE, AVALIAÇÃO, FREQUÊNCIA, MATERIAL, NOTA E COORDENAÇÃO PELO ID SEQUENCIAL ---
    public TurmaDisciplina buscarPorId(Long id) {
        return findById(TurmaDisciplina.class, id)
                .orElseThrow(() -> new BusinessException("Vínculo entre turma e disciplina não encontrado."));
    }
}
