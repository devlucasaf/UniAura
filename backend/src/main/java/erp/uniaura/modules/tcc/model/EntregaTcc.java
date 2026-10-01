package erp.uniaura.modules.tcc.model;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "entregaTcc")
public class EntregaTcc extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tccId", nullable = false, foreignKey = @ForeignKey(name = "fkEntregaTccTcc"))
    private Tcc tcc;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoEntregaTcc tipo;

    @Column(name = "arquivoUrl", nullable = false, length = 500)
    private String arquivoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusEntregaTcc status;

    @Column(name = "observacoesOrientador", length = 1000)
    private String observacoesOrientador;

    @Column(name = "analisadoEm")
    private LocalDateTime analisadoEm;

    @CreationTimestamp
    @Column(name = "enviadoEm", nullable = false, updatable = false)
    private LocalDateTime enviadoEm;
}
