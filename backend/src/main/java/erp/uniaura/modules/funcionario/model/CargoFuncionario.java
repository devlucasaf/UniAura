package erp.uniaura.modules.funcionario.model;

import erp.uniaura.modules.usuario.model.TipoUsuario;

public enum CargoFuncionario {
    COORDENADOR,
    SECRETARIA,
    BIBLIOTECARIO,
    FINANCEIRO,
    ADMIN;

    public TipoUsuario toRoleUsuario() {
        return TipoUsuario.valueOf(this.name());
    }
}

