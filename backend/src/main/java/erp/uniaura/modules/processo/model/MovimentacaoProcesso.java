package erp.uniaura.modules.processo.model;

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

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movimentacaoProcesso")
public class MovimentacaoProcesso {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "processoId", nullable = false,
            foreignKey = @ForeignKey(name = "fkMovimentacaoProcesso"))
    private Processo processo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autorId", nullable = false,
            foreignKey = @ForeignKey(name = "fkMovimentacaoProcessoAutor"))
    private Usuario autor;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusAnterior", length = 20)
    private StatusProcesso statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusNovo", nullable = false, length = 20)
    private StatusProcesso statusNovo;

    @Column(name = "comentario", length = 4000)
    private String comentario;

    // --- QUANDO FALSO, A MOVIMENTAÇÃO É UM DESPACHO INTERNO E NÃO APARECE PARA O ALUNO ---
    @Column(name = "visivelParaAluno", nullable = false)
    private Boolean visivelParaAluno;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
