package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

import java.util.Set;

public enum StatusManifestacao {

    ABERTA,
    EM_APURACAO,
    RESPONDIDA,
    ENCERRADA,
    ARQUIVADA;

    private static final Set<StatusManifestacao> FINAIS = Set.of(ENCERRADA, ARQUIVADA);

    public boolean isFinal() {
        return FINAIS.contains(this);
    }

    // --- TEXTO GRAVADO NO BANCO (O MESMO NOME DA CONSTANTE; O CONVERSOR USA ESTE VALOR) ---
    public String getId() {
        return name();
    }

    public static Optional<StatusManifestacao> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}
