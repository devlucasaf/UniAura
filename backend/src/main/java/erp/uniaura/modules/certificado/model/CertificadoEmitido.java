package erp.uniaura.modules.certificado.model;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.usuario.model.Usuario;

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
@Table(name = "certificadoEmitido")
public class CertificadoEmitido extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false, foreignKey = @ForeignKey(name = "fkCertificadoEmitidoAluno"))
    private Aluno aluno;

    @Enumerated(EnumType.STRING)
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
