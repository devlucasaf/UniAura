package erp.uniaura.modules.ouvidoria.model;

import erp.uniaura.modules.usuario.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "manifestacao")
public class Manifestacao {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "protocolo", nullable = false, unique = true, length = 30)
    private String protocolo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autorId", nullable = false, foreignKey = @ForeignKey(name = "fkManifestacaoAutor"))
    private Usuario autor;

    @Column(name = "anonima", nullable = false)
    private Boolean anonima;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoManifestacao tipo;

    @Column(name = "assunto", nullable = false, length = 200)
    private String assunto;

    @Column(name = "descricao", nullable = false, length = 4000)
    private String descricao;

    @Column(name = "setor", length = 120)
    private String setor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusManifestacao status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavelId", foreignKey = @ForeignKey(name = "fkManifestacaoResponsavel"))
    private Usuario responsavel;

    @Column(name = "prazoResposta")
    private LocalDate prazoResposta;

    @Column(name = "respostaFinal", length = 4000)
    private String respostaFinal;

    @Column(name = "respondidoEm")
    private LocalDateTime respondidoEm;

    @Column(name = "encerradoEm")
    private LocalDateTime encerradoEm;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}
