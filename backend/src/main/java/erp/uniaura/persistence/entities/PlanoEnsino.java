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
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "planoEnsino", uniqueConstraints = @UniqueConstraint(
                name = "ukPlanoEnsinoTurmaDisciplina", columnNames = {"turmaDisciplinaId"})
)
public class PlanoEnsino extends SqlServerBaseEntity {

    // Cardinalidade esperada: 1 para 1 (garantida por unique = true no JoinColumn).

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "turmaDisciplinaId", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fkPlanoEnsinoTurmaDisciplina"))
    private TurmaDisciplina turmaDisciplina;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professorId", nullable = false, foreignKey = @ForeignKey(name = "fkPlanoEnsinoProfessor"))
    private Professor professor;

    @Column(name = "ementa", nullable = false, length = 4000)
    private String ementa;

    @Column(name = "objetivos", nullable = false, length = 4000)
    private String objetivos;

    @Column(name = "conteudoProgramatico", nullable = false, length = 8000)
    private String conteudoProgramatico;

    @Column(name = "metodologia", length = 4000)
    private String metodologia;

    @Column(name = "criteriosAvaliacao", nullable = false, length = 4000)
    private String criteriosAvaliacao;

    @Column(name = "bibliografiaBasica", nullable = false, length = 4000)
    private String bibliografiaBasica;

    @Column(name = "bibliografiaComplementar", length = 4000)
    private String bibliografiaComplementar;
    @Column(name = "status", nullable = false, length = 20)
    private StatusPlanoEnsino status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliadoPorId", foreignKey = @ForeignKey(name = "fkPlanoEnsinoAvaliadoPor"))
    private Usuario avaliadoPor;

    @Column(name = "parecer", length = 4000)
    private String parecer;

    @Column(name = "submetidoEm")
    private LocalDateTime submetidoEm;

    @Column(name = "avaliadoEm")
    private LocalDateTime avaliadoEm;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}
