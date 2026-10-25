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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "acompanhamentoEspecial")
public class AcompanhamentoEspecial extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false, foreignKey = @ForeignKey(name = "fkAcompanhamentoEspecialAluno"))
    private Aluno aluno;

    @Column(name = "tipoNecessidade", nullable = false, length = 40)
    private TipoNecessidadeEspecial tipoNecessidade;

    @Column(name = "descricaoNecessidade", length = 1000)
    private String descricaoNecessidade;

    @Column(name = "recursosNecessarios", length = 1000)
    private String recursosNecessarios;

    @Column(name = "possuiLaudo", nullable = false)
    private Boolean possuiLaudo;

    @Column(name = "status", nullable = false, length = 20)
    private StatusAcompanhamento status;

    @Column(name = "dataInicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "dataEncerramento")
    private LocalDate dataEncerramento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavelPeloAcompanhamentoId", foreignKey = @ForeignKey(name = "fkAcompanhamentoEspecialResponsavel"))
    private Usuario responsavelPeloAcompanhamento;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}
