package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

public enum TipoEndereco {
    RESIDENCIAL,
    PROFISSIONAL;

    // --- TEXTO GRAVADO NO BANCO (O MESMO NOME DA CONSTANTE; O CONVERSOR USA ESTE VALOR) ---
    public String getId() {
        return name();
    }

    public static Optional<TipoEndereco> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}
