package erp.uniaura.modules.tcc.model;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import erp.uniaura.modules.professor.model.Professor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bancaTccMembro", uniqueConstraints = @UniqueConstraint(
                name = "ukBancaTccMembroTccProfessor", columnNames = {"tccId", "professorId"})
)
public class BancaTccMembro extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tccId", nullable = false, foreignKey = @ForeignKey(name = "fkBancaTccMembroTcc"))
    private Tcc tcc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professorId", nullable = false, foreignKey = @ForeignKey(name = "fkBancaTccMembroProfessor"))
    private Professor professor;

    @Column(name = "nota", precision = 4, scale = 2)
    private BigDecimal nota;

    @Column(name = "parecer", length = 1000)
    private String parecer;
}
