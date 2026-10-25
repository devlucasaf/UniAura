package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

public enum CargoFuncionario {
    COORDENADOR,
    SECRETARIA,
    BIBLIOTECARIO,
    FINANCEIRO,
    ADMIN;

    public TipoUsuario toRoleUsuario() {
        return TipoUsuario.valueOf(this.name());
    }

    // --- TEXTO GRAVADO NO BANCO ---
    public String getId() {
        return name();
    }

    public static Optional<CargoFuncionario> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}

