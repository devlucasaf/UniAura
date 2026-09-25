package erp.uniaura.modules.coordenacao.model;

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
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

// --- CONVOCADO PARA UMA REUNIÃO DO COLEGIADO E A SUA PRESENÇA ---
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "participanteReuniao",
        uniqueConstraints = @UniqueConstraint(
                name = "ukParticipanteReuniao",
                columnNames = {"reuniaoId", "usuarioId"}
        )
)
public class ParticipanteReuniao {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reuniaoId", nullable = false,
            foreignKey = @ForeignKey(name = "fkParticipanteReuniao"))
    private ReuniaoColegiado reuniao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarioId", nullable = false,
            foreignKey = @ForeignKey(name = "fkParticipanteReuniaoUsuario"))
    private Usuario usuario;

    @Column(name = "papel", length = 100)
    private String papel;

    // --- NULO ENQUANTO A REUNIÃO NÃO É REALIZADA ---
    @Column(name = "presente")
    private Boolean presente;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
