package erp.uniaura.modules.estagio.model;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.professor.model.Professor;

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
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estagio")
public class Estagio extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false, foreignKey = @ForeignKey(name = "fkEstagioAluno"))
    private Aluno aluno;

    @Column(name = "empresaConcedente", nullable = false, length = 200)
    private String empresaConcedente;

    @Column(name = "supervisorEmpresa", length = 150)
    private String supervisorEmpresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professorOrientadorId", foreignKey = @ForeignKey(name = "fkEstagioProfessorOrientador"))
    private Professor professorOrientador;

    @Column(name = "dataInicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "dataFimPrevista", nullable = false)
    private LocalDate dataFimPrevista;

    @Column(name = "dataFimEfetiva")
    private LocalDate dataFimEfetiva;

    @Column(name = "cargaHorariaTotal", nullable = false)
    private Integer cargaHorariaTotal;

    @Column(name = "cargaHorariaCumprida", nullable = false)
    private Integer cargaHorariaCumprida;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusEstagio status;

    @Column(name = "termoCompromissoUrl", length = 500)
    private String termoCompromissoUrl;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}
