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
@Table(name = "turma")
public class Turma extends SqlServerBaseEntity {

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cursoId", nullable = false, foreignKey = @ForeignKey(name = "fkTurmaCurso"))
    private Curso curso;

    @Column(name = "periodoLetivo", nullable = false, length = 20)
    private String periodoLetivo;

    @Column(name = "serie", nullable = false, length = 50)
    private String serie;

    @Column(name = "sala", length = 50)
    private String sala;
    @Column(name = "turno", nullable = false, length = 20)
    private Turno turno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professorRegenteId", foreignKey = @ForeignKey(name = "fkTurmaProfessorRegente"))
    private Professor professorRegente;

    @Column(name = "capacidadeMaxima", nullable = false)
    private Integer capacidadeMaxima;

    @Column(name = "ativa", nullable = false)
    private Boolean ativa;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}

