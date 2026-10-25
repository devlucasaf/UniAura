package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "nota")
public class Nota extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false, foreignKey = @ForeignKey(name = "fkNotaAluno"))
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "turmaDisciplinaId", nullable = false, foreignKey = @ForeignKey(name = "fkNotaTurmaDisciplina"))
    private TurmaDisciplina turmaDisciplina;

    @Column(name = "periodoAvaliacao", nullable = false, length = 30)
    private PeriodoAvaliacao periodoAvaliacao;

    @Column(name = "tipoAvaliacao", nullable = false, length = 20)
    private TipoAvaliacao tipoAvaliacao;

    @Column(name = "valor", nullable = false, precision = 5, scale = 2)
    private BigDecimal valor;

    @Column(name = "peso", nullable = false, precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "observacoes", length = 1000)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lancadaPor", foreignKey = @ForeignKey(name = "fkNotaLancadaPor"))
    private Usuario lancadaPor;

    @CreationTimestamp
    @Column(name = "lancadaEm", nullable = false, updatable = false)
    private LocalDateTime lancadaEm;

    @UpdateTimestamp
    @Column(name = "atualizadaEm", nullable = false)
    private LocalDateTime atualizadaEm;
}
