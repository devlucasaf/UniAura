package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "professorDisciplina", uniqueConstraints = @UniqueConstraint(name = "ukProfessorDisciplina", columnNames = {"professorId", "disciplinaId"}))
public class ProfessorDisciplina extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professorId", nullable = false, foreignKey = @ForeignKey(name = "fkProfessorDisciplinaProfessor"))
    private Professor professor;

    @Column(name = "disciplinaId", nullable = false)
    private Long disciplinaId;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}

