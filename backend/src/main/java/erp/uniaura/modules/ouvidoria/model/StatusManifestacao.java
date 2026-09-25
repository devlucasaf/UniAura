package erp.uniaura.modules.ouvidoria.model;

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
}
