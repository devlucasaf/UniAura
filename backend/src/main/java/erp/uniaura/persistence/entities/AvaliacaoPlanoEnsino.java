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

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "avaliacaoPlanoEnsino")
public class AvaliacaoPlanoEnsino extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "planoEnsinoId", nullable = false, foreignKey = @ForeignKey(name = "fkAvaliacaoPlanoEnsino"))
    private PlanoEnsino planoEnsino;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autorId", nullable = false, foreignKey = @ForeignKey(name = "fkAvaliacaoPlanoEnsinoAutor"))
    private Usuario autor;

    @Column(name = "statusAnterior", length = 20)
    private StatusPlanoEnsino statusAnterior;

    @Column(name = "statusNovo", nullable = false, length = 20)
    private StatusPlanoEnsino statusNovo;

    @Column(name = "parecer", length = 4000)
    private String parecer;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
