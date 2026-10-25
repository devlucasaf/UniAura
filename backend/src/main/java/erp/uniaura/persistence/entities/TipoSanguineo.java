package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

public enum TipoSanguineo {
    A_POSITIVO,
    A_NEGATIVO,
    B_POSITIVO,
    B_NEGATIVO,
    AB_POSITIVO,
    AB_NEGATIVO,
    O_POSITIVO,
    O_NEGATIVO;

    // --- TEXTO GRAVADO NO BANCO (O MESMO NOME DA CONSTANTE; O CONVERSOR USA ESTE VALOR) ---
    public String getId() {
        return name();
    }

    public static Optional<TipoSanguineo> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}
