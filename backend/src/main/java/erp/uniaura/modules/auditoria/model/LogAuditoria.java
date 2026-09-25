package erp.uniaura.modules.auditoria.model;

import erp.uniaura.modules.usuario.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "logAuditoria")
public class LogAuditoria {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", foreignKey = @ForeignKey(name = "fkLogAuditoriaUsuario"))
    private Usuario usuario;

    @Column(name = "acao", nullable = false, length = 60)
    private String acao;

    @Column(name = "entidade", nullable = false, length = 60)
    private String entidade;

    @Column(name = "entidadeId", length = 60)
    private String entidadeId;

    @Column(name = "detalhes", length = 1000)
    private String detalhes;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
