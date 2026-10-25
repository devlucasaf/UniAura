package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "material")
public class Material extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "turmaDisciplinaId", nullable = false, foreignKey = @ForeignKey(name = "fkMaterialTurmaDisciplina"))
    private TurmaDisciplina turmaDisciplina;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descricao", length = 2000)
    private String descricao;
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoMaterial tipo;

    @Column(name = "arquivoUrl", length = 500)
    private String arquivoUrl;

    @Column(name = "linkUrl", length = 500)
    private String linkUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professorId", nullable = false, foreignKey = @ForeignKey(name = "fkMaterialProfessor"))
    private Professor professor;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}

