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
@Table(name = "certificadoEmitido")
public class CertificadoEmitido extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false, foreignKey = @ForeignKey(name = "fkCertificadoEmitidoAluno"))
    private Aluno aluno;

    @Column(name = "tipo", nullable = false, length = 30)
    private TipoCertificado tipo;

    @Column(name = "codigoVerificacao", nullable = false, unique = true, length = 36)
    private String codigoVerificacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emitidoPor", nullable = false, foreignKey = @ForeignKey(name = "fkCertificadoEmitidoEmitidoPor"))
    private Usuario emitidoPor;

    @CreationTimestamp
    @Column(name = "emitidoEm", nullable = false, updatable = false)
    private LocalDateTime emitidoEm;
}
