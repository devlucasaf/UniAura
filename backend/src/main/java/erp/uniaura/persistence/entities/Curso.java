package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "curso")
public class Curso extends SqlServerBaseEntity {

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "descricao", length = 1000)
    private String descricao;

    @Column(name = "nivel", nullable = false, length = 20)
    private NivelCurso nivel;

    @Column(name = "duracaoSemestres", nullable = false)
    private Integer duracaoSemestres;

    @Column(name = "cargaHorariaTotal", nullable = false)
    private Integer cargaHorariaTotal;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}

