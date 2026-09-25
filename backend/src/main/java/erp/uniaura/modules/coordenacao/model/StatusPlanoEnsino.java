package erp.uniaura.modules.coordenacao.model;

import java.util.Set;

public enum StatusPlanoEnsino {

    RASCUNHO,
    SUBMETIDO,
    DEVOLVIDO,
    APROVADO,
    REPROVADO;

    private static final Set<StatusPlanoEnsino> FINAIS = Set.of(APROVADO, REPROVADO);

    // --- ESTADOS EM QUE O PROFESSOR AINDA PODE EDITAR O CONTEÚDO DO PLANO ---
    private static final Set<StatusPlanoEnsino> EDITAVEIS = Set.of(RASCUNHO, DEVOLVIDO);

    public boolean isFinal() {
        return FINAIS.contains(this);
    }

    public boolean isEditavel() {
        return EDITAVEIS.contains(this);
    }
}
