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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "registroDeAcompanhamento")
public class RegistroDeAcompanhamento extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "acompanhamentoId", nullable = false, foreignKey = @ForeignKey(name = "fkRegistroDeAcompanhamentoAcompanhamento"))
    private AcompanhamentoEspecial acompanhamento;

    @Column(name = "tipo", nullable = false, length = 30)
    private TipoRegistroAcompanhamento tipo;

    @Column(name = "dataRegistro", nullable = false)
    private LocalDate dataRegistro;

    @Column(name = "descricao", nullable = false, length = 2000)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autorId", nullable = false, foreignKey = @ForeignKey(name = "fkRegistroDeAcompanhamentoAutor"))
    private Usuario autor;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
