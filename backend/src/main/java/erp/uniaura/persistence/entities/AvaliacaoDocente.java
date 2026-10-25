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
@Table(name = "avaliacaoDocente", uniqueConstraints = @UniqueConstraint(
                name = "ukAvaliacaoDocenteAlunoTurmaDisciplina", columnNames = {"alunoId", "turmaDisciplinaId"})
)
public class AvaliacaoDocente extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false, foreignKey = @ForeignKey(name = "fkAvaliacaoDocenteAluno"))
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "turmaDisciplinaId", nullable = false, foreignKey = @ForeignKey(name = "fkAvaliacaoDocenteTurmaDisciplina"))
    private TurmaDisciplina turmaDisciplina;

    @Column(name = "notaDidatica", nullable = false)
    private Integer notaDidatica;

    @Column(name = "notaPontualidade", nullable = false)
    private Integer notaPontualidade;

    @Column(name = "notaDisponibilidade", nullable = false)
    private Integer notaDisponibilidade;

    @Column(name = "comentario", length = 1000)
    private String comentario;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
