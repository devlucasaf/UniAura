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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "mensalidade")
public class Mensalidade extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false, foreignKey = @ForeignKey(name = "fkMensalidadeAluno"))
    private Aluno aluno;

    @Column(name = "competencia", nullable = false, length = 7)
    private String competencia;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "vencimento", nullable = false)
    private LocalDate vencimento;
    @Column(name = "status", nullable = false, length = 20)
    private StatusMensalidade status;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}
