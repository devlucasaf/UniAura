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
@Table(name = "exemplar")
public class Exemplar extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "livroId", nullable = false, foreignKey = @ForeignKey(name = "fkExemplarLivro"))
    private Livro livro;

    @Column(name = "codigoBarras", nullable = false, unique = true, length = 50)
    private String codigoBarras;

    @Column(name = "localizacao", length = 50)
    private String localizacao;
    @Column(name = "status", nullable = false, length = 20)
    private StatusExemplar status;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}

