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
@Table(name = "movimentacaoProcesso")
public class MovimentacaoProcesso extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "processoId", nullable = false, foreignKey = @ForeignKey(name = "fkMovimentacaoProcesso"))
    private Processo processo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autorId", nullable = false, foreignKey = @ForeignKey(name = "fkMovimentacaoProcessoAutor"))
    private Usuario autor;
    @Column(name = "statusAnterior", length = 20)
    private StatusProcesso statusAnterior;
    @Column(name = "statusNovo", nullable = false, length = 20)
    private StatusProcesso statusNovo;

    @Column(name = "comentario", length = 4000)
    private String comentario;

    @Column(name = "visivelParaAluno", nullable = false)
    private Boolean visivelParaAluno;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
