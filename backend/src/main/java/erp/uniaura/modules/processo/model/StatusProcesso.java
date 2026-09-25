package erp.uniaura.modules.processo.model;

import java.util.Set;

public enum StatusProcesso {

    ABERTO,
    EM_ANALISE,
    AGUARDANDO_ALUNO,
    DEFERIDO,
    INDEFERIDO,
    CANCELADO;

    private static final Set<StatusProcesso> FINAIS = Set.of(DEFERIDO, INDEFERIDO, CANCELADO);

    public boolean isFinal() {
        return FINAIS.contains(this);
    }
}
