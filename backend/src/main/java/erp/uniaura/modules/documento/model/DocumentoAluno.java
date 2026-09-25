package erp.uniaura.modules.documento.model;

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

import java.time.LocalDateTime;
import java.util.UUID;

// --- DOCUMENTO ENVIADO PELO ALUNO (OU PELA SECRETARIA EM SEU NOME) PARA A MATRÍCULA ---
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "documentoAluno")
public class DocumentoAluno {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false,
            foreignKey = @ForeignKey(name = "fkDocumentoAlunoAluno"))
    private Aluno aluno;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 40)
    private TipoDocumento tipo;

    @Column(name = "nomeArquivo", nullable = false, length = 255)
    private String nomeArquivo;

    @Column(name = "arquivoUrl", nullable = false, length = 500)
    private String arquivoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusDocumento status;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analisadoPor", foreignKey = @ForeignKey(name = "fkDocumentoAlunoAnalisadoPor"))
    private Usuario analisadoPor;

    @Column(name = "analisadoEm")
    private LocalDateTime analisadoEm;

    @CreationTimestamp
    @Column(name = "enviadoEm", nullable = false, updatable = false)
    private LocalDateTime enviadoEm;
}
