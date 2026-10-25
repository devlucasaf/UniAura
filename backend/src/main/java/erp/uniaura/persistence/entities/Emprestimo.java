package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "emprestimo")
public class Emprestimo extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exemplarId", nullable = false, foreignKey = @ForeignKey(name = "fkEmprestimoExemplar"))
    private Exemplar exemplar;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarioId", nullable = false, foreignKey = @ForeignKey(name = "fkEmprestimoUsuario"))
    private Usuario usuario;

    @Column(name = "dataEmprestimo", nullable = false)
    private LocalDateTime dataEmprestimo;

    @Column(name = "dataDevolucaoPrevista", nullable = false)
    private LocalDateTime dataDevolucaoPrevista;

    @Column(name = "dataDevolucaoEfetiva")
    private LocalDateTime dataDevolucaoEfetiva;

    @Column(name = "renovacoes", nullable = false)
    private Integer renovacoes;

    @Column(name = "status", nullable = false, length = 20)
    private StatusEmprestimo status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "criadoPorId", nullable = false,  foreignKey = @ForeignKey(name = "fkEmprestimoCriadoPor"))
    private Usuario criadoPor;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}

