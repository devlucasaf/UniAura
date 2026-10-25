package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

public enum StatusAcompanhamento {
    ATIVO,
    SUSPENSO,
    ENCERRADO;

    public String getId() {
        return name();
    }

    public static Optional<StatusAcompanhamento> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}
