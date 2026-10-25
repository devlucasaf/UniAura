package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

public enum TipoProcesso {
    TRANCAMENTO_MATRICULA,
    REABERTURA_MATRICULA,
    TRANSFERENCIA_CURSO,
    APROVEITAMENTO_DISCIPLINA,
    DISPENSA_DISCIPLINA,
    REVISAO_NOTA,
    SEGUNDA_CHAMADA_PROVA,
    DECLARACAO_MATRICULA,
    HISTORICO_ESCOLAR,
    COLACAO_GRAU,
    OUTROS;

    // --- TEXTO GRAVADO NO BANCO (O MESMO NOME DA CONSTANTE; O CONVERSOR USA ESTE VALOR) ---
    public String getId() {
        return name();
    }

    public static Optional<TipoProcesso> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}
