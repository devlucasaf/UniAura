package erp.uniaura.modules.coordenacao.model;

import java.util.Set;

public enum StatusReuniao {

    AGENDADA,
    REALIZADA,
    CANCELADA;

    private static final Set<StatusReuniao> FINAIS = Set.of(REALIZADA, CANCELADA);

    public boolean isFinal() {
        return FINAIS.contains(this);
    }
}
