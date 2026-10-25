package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "configuracaoBiblioteca")
public class ConfiguracaoBiblioteca extends SqlServerBaseEntity {

    @Column(name = "prazoEmprestimoAluno", nullable = false)
    private Integer prazoEmprestimoAluno;

    @Column(name = "prazoEmprestimoProfessor", nullable = false)
    private Integer prazoEmprestimoProfessor;

    @Column(name = "maxEmprestimosSimultaneos", nullable = false)
    private Integer maximosEmprestimosSimultaneos;

    @Column(name = "maxRenovacoes", nullable = false)
    private Integer maximasRenovacoes;

    @Column(name = "valorMultaDia", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMultaDia;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}

