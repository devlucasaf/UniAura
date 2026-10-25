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
@Table(name = "relatorioEstagio")
public class RelatorioEstagio extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estagioId", nullable = false, foreignKey = @ForeignKey(name = "fkRelatorioEstagioEstagio"))
    private Estagio estagio;

    @Column(name = "periodoReferencia", nullable = false, length = 7)
    private String periodoReferencia;

    @Column(name = "horasRegistradas", nullable = false)
    private Integer horasRegistradas;

    @Column(name = "descricaoAtividades", length = 2000)
    private String descricaoAtividades;

    @Column(name = "comprovanteUrl", length = 500)
    private String comprovanteUrl;
    @Column(name = "status", nullable = false, length = 20)
    private StatusRelatorioEstagio status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analisadoPor", foreignKey = @ForeignKey(name = "fkRelatorioEstagioAnalisadoPor"))
    private Usuario analisadoPor;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @Column(name = "analisadoEm")
    private LocalDateTime analisadoEm;

    @CreationTimestamp
    @Column(name = "enviadoEm", nullable = false, updatable = false)
    private LocalDateTime enviadoEm;
}
