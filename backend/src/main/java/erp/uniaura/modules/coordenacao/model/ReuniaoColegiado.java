package erp.uniaura.modules.coordenacao.model;

import erp.uniaura.modules.curso.model.Curso;
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

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reuniaoColegiado")
public class ReuniaoColegiado {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cursoId", nullable = false,
            foreignKey = @ForeignKey(name = "fkReuniaoColegiadoCurso"))
    private Curso curso;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "dataHora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "local", length = 150)
    private String local;

    @Column(name = "pauta", nullable = false, length = 8000)
    private String pauta;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusReuniao status;

    // --- PREENCHIDA NO REGISTRO DA ATA ---
    @Column(name = "deliberacoes", length = 8000)
    private String deliberacoes;

    @Column(name = "motivoCancelamento", length = 2000)
    private String motivoCancelamento;

    @Column(name = "encerradaEm")
    private LocalDateTime encerradaEm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "criadaPorId", nullable = false,
            foreignKey = @ForeignKey(name = "fkReuniaoColegiadoCriadaPor"))
    private Usuario criadaPor;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}
