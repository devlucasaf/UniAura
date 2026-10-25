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

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reuniaoColegiado")
public class ReuniaoColegiado extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cursoId", nullable = false, foreignKey = @ForeignKey(name = "fkReuniaoColegiadoCurso"))
    private Curso curso;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "dataHora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "local", length = 150)
    private String local;

    @Column(name = "pauta", nullable = false, length = 8000)
    private String pauta;
    @Column(name = "status", nullable = false, length = 20)
    private StatusReuniao status;

    @Column(name = "deliberacoes", length = 8000)
    private String deliberacoes;

    @Column(name = "motivoCancelamento", length = 2000)
    private String motivoCancelamento;

    @Column(name = "encerradaEm")
    private LocalDateTime encerradaEm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "criadaPorId", nullable = false, foreignKey = @ForeignKey(name = "fkReuniaoColegiadoCriadaPor"))
    private Usuario criadaPor;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}
