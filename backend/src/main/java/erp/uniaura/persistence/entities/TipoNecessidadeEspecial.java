package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

public enum TipoNecessidadeEspecial {
    DEFICIENCIA_FISICA,
    DEFICIENCIA_VISUAL,
    DEFICIENCIA_AUDITIVA,
    DEFICIENCIA_INTELECTUAL,
    TRANSTORNO_DO_ESPECTRO_AUTISTA,
    ALTAS_HABILIDADES,
    TRANSTORNO_DE_APRENDIZAGEM,
    OUTRA;

    public String getId() {
        return name();
    }

    public static Optional<TipoNecessidadeEspecial> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}
