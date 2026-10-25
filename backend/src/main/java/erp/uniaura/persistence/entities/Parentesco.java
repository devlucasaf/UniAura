package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

public enum Parentesco {
    PAI,
    MAE,
    TUTOR,
    OUTRO;

    public String getId() {
        return name();
    }

    public static Optional<Parentesco> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}

