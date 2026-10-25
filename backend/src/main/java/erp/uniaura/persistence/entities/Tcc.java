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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tcc")
public class Tcc extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false, foreignKey = @ForeignKey(name = "fkTccAluno"))
    private Aluno aluno;

    @Column(name = "titulo", nullable = false, length = 300)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professorOrientadorId", nullable = false, foreignKey = @ForeignKey(name = "fkTccProfessorOrientador"))
    private Professor professorOrientador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professorCoorientadorId", foreignKey = @ForeignKey(name = "fkTccProfessorCoorientador"))
    private Professor professorCoorientador;

    @Column(name = "periodoLetivo", nullable = false, length = 10)
    private String periodoLetivo;
    @Column(name = "status", nullable = false, length = 20)
    private StatusTcc status;

    @Column(name = "notaFinal", precision = 4, scale = 2)
    private BigDecimal notaFinal;

    @Column(name = "dataDefesa")
    private LocalDate dataDefesa;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}
