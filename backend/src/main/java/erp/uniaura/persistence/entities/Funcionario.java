package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "funcionario")
public class Funcionario extends SqlServerBaseEntity {

    // Cardinalidade esperada: 1 para 1 (garantida por unique = true no JoinColumn).

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarioId", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fkFuncionarioUsuario"))
    private Usuario usuario;
    @Column(name = "cargo", nullable = false, length = 30)
    private CargoFuncionario cargo;

    @Column(name = "dataAdmissao", nullable = false)
    private LocalDate dataAdmissao;

    @Column(name = "departamento", length = 100)
    private String departamento;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}

