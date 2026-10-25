package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

import java.util.Set;

public enum StatusProcesso {
    ABERTO,
    EM_ANALISE,
    AGUARDANDO_ALUNO,
    DEFERIDO,
    INDEFERIDO,
    CANCELADO;

    private static final Set<StatusProcesso> FINAIS = Set.of(DEFERIDO, INDEFERIDO, CANCELADO);

    public boolean isFinal() {
        return FINAIS.contains(this);
    }

    // --- TEXTO GRAVADO NO BANCO (O MESMO NOME DA CONSTANTE; O CONVERSOR USA ESTE VALOR) ---
    public String getId() {
        return name();
    }

    public static Optional<StatusProcesso> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}
