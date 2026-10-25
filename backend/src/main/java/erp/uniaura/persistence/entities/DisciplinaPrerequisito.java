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
@Table(name = "disciplinaPrerequisito", uniqueConstraints = @UniqueConstraint(
                name = "ukDisciplinaPrerequisito", columnNames = {"disciplinaId", "prerequisitoId"})
)
public class DisciplinaPrerequisito extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "disciplinaId", nullable = false, foreignKey = @ForeignKey(name = "fkDisciplinaPrerequisitoDisciplina"))
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prerequisitoId", nullable = false, foreignKey = @ForeignKey(name = "fkDisciplinaPrerequisitoPrerequisito"))
    private Disciplina prerequisito;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}

