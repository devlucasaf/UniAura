package erp.academico.modules.comunicado.model;

import erp.academico.modules.usuario.model.Usuario;

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

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comunicado")
public class Comunicado {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "mensagem", nullable = false, length = 2000)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "publicoAlvo", nullable = false, length = 20)
    private PublicoAlvoComunicado publicoAlvo;

    // --- SÓ PREENCHIDO QUANDO publicoAlvo = TURMA ---
    @Column(name = "turmaId")
    private UUID turmaId;

    @Column(name = "importante", nullable = false)
    private Boolean importante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autorId", nullable = false,
            foreignKey = @ForeignKey(name = "fkComunicadoAutor"))
    private Usuario autor;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}
