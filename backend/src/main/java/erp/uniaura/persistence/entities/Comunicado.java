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
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comunicado")
public class Comunicado extends SqlServerBaseEntity {

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "mensagem", nullable = false, length = 2000)
    private String mensagem;

    @Column(name = "publicoAlvo", nullable = false, length = 20)
    private PublicoAlvoComunicado publicoAlvo;

    @Column(name = "turmaId")
    private Long turmaId;

    @Column(name = "importante", nullable = false)
    private Boolean importante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autorId", nullable = false, foreignKey = @ForeignKey(name = "fkComunicadoAutor"))
    private Usuario autor;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}
