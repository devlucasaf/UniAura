package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

import java.util.Set;

public enum StatusReuniao {
    AGENDADA,
    REALIZADA,
    CANCELADA;

    private static final Set<StatusReuniao> FINAIS = Set.of(REALIZADA, CANCELADA);

    public boolean isFinal() {
        return FINAIS.contains(this);
    }

    // --- TEXTO GRAVADO NO BANCO (O MESMO NOME DA CONSTANTE; O CONVERSOR USA ESTE VALOR) ---
    public String getId() {
        return name();
    }

    public static Optional<StatusReuniao> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}
