package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "frequencia", uniqueConstraints = @UniqueConstraint(
                name = "ukFrequenciaAulaAluno", columnNames = {"aulaId", "alunoId"})
)
public class Frequencia extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aulaId", nullable = false, foreignKey = @ForeignKey(name = "fkFrequenciaAula"))
    private Aula aula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false, foreignKey = @ForeignKey(name = "fkFrequenciaAluno"))
    private Aluno aluno;

    @Column(name = "presente", nullable = false)
    private Boolean presente;

    @Column(name = "justificativa", length = 1000)
    private String justificativa;

    @CreationTimestamp
    @Column(name = "criadaEm", nullable = false, updatable = false)
    private LocalDateTime criadaEm;

    @UpdateTimestamp
    @Column(name = "atualizadaEm", nullable = false)
    private LocalDateTime atualizadaEm;
}
