package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "respostaManifestacao")
public class RespostaManifestacao extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manifestacaoId", nullable = false,
            foreignKey = @ForeignKey(name = "fkRespostaManifestacao"))
    private Manifestacao manifestacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autorId", nullable = false,
            foreignKey = @ForeignKey(name = "fkRespostaManifestacaoAutor"))
    private Usuario autor;
    @Column(name = "statusAnterior", length = 20)
    private StatusManifestacao statusAnterior;
    @Column(name = "statusNovo", nullable = false, length = 20)
    private StatusManifestacao statusNovo;

    @Column(name = "mensagem", nullable = false, length = 4000)
    private String mensagem;

    @Column(name = "visivelParaAutor", nullable = false)
    private Boolean visivelParaAutor;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
