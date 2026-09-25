package erp.uniaura.modules.processo.model;

import erp.uniaura.modules.aluno.model.Aluno;
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
@Table(name = "processo")
public class Processo {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "protocolo", nullable = false, unique = true, length = 30)
    private String protocolo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false,
            foreignKey = @ForeignKey(name = "fkProcessoAluno"))
    private Aluno aluno;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 40)
    private TipoProcesso tipo;

    @Column(name = "assunto", nullable = false, length = 200)
    private String assunto;

    @Column(name = "descricao", nullable = false, length = 4000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusProcesso status;

    // --- SERVIDOR DA SECRETARIA/COORDENAÇÃO QUE ASSUMIU A ANÁLISE ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavelId",
            foreignKey = @ForeignKey(name = "fkProcessoResponsavel"))
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
