package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "multa")
public class Multa extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emprestimoId", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fkMultaEmprestimo"))
    private Emprestimo emprestimo;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "diasAtraso", nullable = false)
    private Integer diasAtraso;
    @Column(name = "status", nullable = false, length = 20)
    private StatusMulta status;

    @CreationTimestamp
    @Column(name = "geradaEm", nullable = false, updatable = false)
    private LocalDateTime geradaEm;

    @Column(name = "pagaEm")
    private LocalDateTime pagaEm;
}

