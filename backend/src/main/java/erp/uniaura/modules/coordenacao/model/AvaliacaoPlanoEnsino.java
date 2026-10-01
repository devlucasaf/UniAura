package erp.uniaura.modules.coordenacao.model;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import erp.uniaura.modules.usuario.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "avaliacaoPlanoEnsino")
public class AvaliacaoPlanoEnsino extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "planoEnsinoId", nullable = false, foreignKey = @ForeignKey(name = "fkAvaliacaoPlanoEnsino"))
    private PlanoEnsino planoEnsino;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autorId", nullable = false, foreignKey = @ForeignKey(name = "fkAvaliacaoPlanoEnsinoAutor"))
    private Usuario autor;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusAnterior", length = 20)
    private StatusPlanoEnsino statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusNovo", nullable = false, length = 20)
    private StatusPlanoEnsino statusNovo;

    @Column(name = "parecer", length = 4000)
    private String parecer;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
