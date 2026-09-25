package erp.uniaura.modules.coordenacao.model;

import erp.uniaura.modules.professor.model.Professor;
import erp.uniaura.modules.turma.model.TurmaDisciplina;
import erp.uniaura.modules.usuario.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "planoEnsino",
        uniqueConstraints = @UniqueConstraint(
                name = "ukPlanoEnsinoTurmaDisciplina",
                columnNames = {"turmaDisciplinaId"}
        )
)
public class PlanoEnsino {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "turmaDisciplinaId", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fkPlanoEnsinoTurmaDisciplina"))
    private TurmaDisciplina turmaDisciplina;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professorId", nullable = false,
            foreignKey = @ForeignKey(name = "fkPlanoEnsinoProfessor"))
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusPlanoEnsino status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliadoPorId",
            foreignKey = @ForeignKey(name = "fkPlanoEnsinoAvaliadoPor"))
    private Usuario avaliadoPor;

    // --- PARECER DA COORDENAÇÃO NA ÚLTIMA AVALIAÇÃO ---
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
