package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

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

    // --- TEXTO GRAVADO NO BANCO (O MESMO NOME DA CONSTANTE; O CONVERSOR USA ESTE VALOR) ---
    public String getId() {
        return name();
    }

    public static Optional<StatusPlanoEnsino> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}
