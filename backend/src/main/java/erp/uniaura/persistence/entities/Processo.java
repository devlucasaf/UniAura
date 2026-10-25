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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "processo")
public class Processo extends SqlServerBaseEntity {

    @Column(name = "protocolo", nullable = false, unique = true, length = 30)
    private String protocolo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false, foreignKey = @ForeignKey(name = "fkProcessoAluno"))
    private Aluno aluno;
    @Column(name = "tipo", nullable = false, length = 40)
    private TipoProcesso tipo;

    @Column(name = "assunto", nullable = false, length = 200)
    private String assunto;

    @Column(name = "descricao", nullable = false, length = 4000)
    private String descricao;
    @Column(name = "status", nullable = false, length = 20)
    private StatusProcesso status;

    // --- SERVIDOR DA SECRETARIA/COORDENAÇÃO QUE ASSUMIU A ANÁLISE ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavelId", foreignKey = @ForeignKey(name = "fkProcessoResponsavel"))
    private Usuario responsavel;

    @Column(name = "prazoResposta")
    private LocalDate prazoResposta;

    // --- PARECER REGISTRADO NO ENCERRAMENTO ---
    @Column(name = "parecerFinal", length = 4000)
    private String parecerFinal;

    @Column(name = "encerradoEm")
    private LocalDateTime encerradoEm;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}
