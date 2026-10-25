package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

public enum TipoRegistroAcompanhamento {
    ATENDIMENTO,
    ADAPTACAO_PEDAGOGICA,
    CONTATO_COM_FAMILIA,
    ENCAMINHAMENTO,
    OCORRENCIA;

    public String getId() {
        return name();
    }

    public static Optional<TipoRegistroAcompanhamento> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}
