package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "turmaDisciplina", uniqueConstraints = @UniqueConstraint(
                name = "ukTurmaDisciplina", columnNames = {"turmaId", "disciplinaId"})
)
public class TurmaDisciplina extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "turmaId", nullable = false, foreignKey = @ForeignKey(name = "fkTurmaDisciplinaTurma"))
    private Turma turma;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "disciplinaId", nullable = false, foreignKey = @ForeignKey(name = "fkTurmaDisciplinaDisciplina"))
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professorId", nullable = false, foreignKey = @ForeignKey(name = "fkTurmaDisciplinaProfessor"))
    private Professor professor;
    @Column(name = "diaSemana", nullable = false, length = 20)
    private DiaSemana diaSemana;

    @Column(name = "horarioInicio", nullable = false)
    private LocalTime horarioInicio;

    @Column(name = "horarioFim", nullable = false)
    private LocalTime horarioFim;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
